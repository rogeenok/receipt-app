package com.netcracker.checkapp.server.service.checkservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.checkapp.server.model.FDSP;
import com.netcracker.checkapp.server.model.check.Check;
import com.netcracker.checkapp.server.model.check.Converter;
import com.netcracker.checkapp.server.model.check.NalogRuCheck;
import com.netcracker.checkapp.server.persistance.CheckRepository;
import com.netcracker.checkapp.server.service.fdspservice.FDSPService;
import com.netcracker.checkapp.server.service.httpservice.HttpService;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService {
    private String NALOG_RU = "http://proverkacheka.nalog.ru:8888/v1/inns/*/kkts/*/" +
            "fss/%s/tickets/%s?fiscalSign=%s&sendToEmail=no";
    private final static String AUTHORIZATION = "Authorization";
    private final static String AUTHORIZATION_VALUE = "Basic Kzc5MTE3OTcyMDY0OjExMDM1MQ==";
    private final static String DEVICE_ID = "Device-Id";
    private final static String DEVICE_ID_VALUE = "546112";
    private final static String DEVICE_OS = "Device-OS";
    private final static String DEVICE_OS_ID = "Adnroid 6.0.1";
    private final static String VERSION = "Version";
    private final static String VERSION_ID = "2";
    private final static String CLIENT_VERSION = "ClientVersion";
    private final static String CLIENT_VERSION_ID = "1.4.2";
    private final static String HOST = "Host";
    private final static String HOST_ID = "proverkacheka.nalog.ru:8888";
    private final static String USER_AGENT = "User-Agent";
    private final static String USER_AGENT_ID = "okhttp/3.0.1";
    private final static String ROOT = "/document/receipt";

    private CheckRepository checkRepository;
    private FDSPService fdspService;
    private HttpService httpService;

    public CheckServiceImpl(FDSPService fdspService,
                            CheckRepository checkRepository,
                            HttpService httpService) {
        this.fdspService = fdspService;
        this.checkRepository = checkRepository;
        this.httpService = httpService;
    }

    @Override
    public Check getCheck(Check check) {
        Map<String, String> headers;
        NalogRuCheck nalogRuCheck;
        ObjectMapper objectMapper = new ObjectMapper();
        Check localCheck = null;

        headers = buildHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<String>(httpService.createHttpHeaders(headers));
        try {
            JsonNode node = objectMapper.readTree(new RestTemplate().exchange(String.format(NALOG_RU,
                    check.getFiscalDriveNumber(), check.getFiscalDocumentNumber(), check.getFiscalSign()),
                    HttpMethod.GET, httpEntity, String.class).getBody());
            nalogRuCheck = objectMapper.treeToValue(node.at(ROOT), NalogRuCheck.class);

            localCheck = Converter.fromNalogRuCheckToCheck(nalogRuCheck);
            localCheck.setShortPlace(check.getShortPlace());

            FDSP fdsp = new FDSP();
            fdsp.setFiscalDriveNumber(localCheck.getFiscalDriveNumber());
            fdsp.setShortPlace(localCheck.getShortPlace());
            fdspService.addFDSP(fdsp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localCheck;
    }

    @Override
    public List<Check> getNearPlacesAndChecks(String longitude, String latitude, String radius) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Distance distance = new Distance(Double.parseDouble(radius), Metrics.KILOMETERS);
        Point coords = new Point(Double.parseDouble(latitude), Double.parseDouble(longitude));
        return checkRepository.findByUsernameAndShortPlaceCoordsNear(principal.getUsername(), coords, distance);
    }

    @Override
    public Check save(Check check) {
        return checkRepository.save(check);
    }

    @Override
    public boolean existsByIdAndUsername(String id, String username) {
        return checkRepository.existsByIdAndUsername(id, username);
    }

    @Override
    public Check findById(String id) {
        return checkRepository.findOne(id);
    }

    @Override
    public List<Check> findByUsername(String username) {
        return checkRepository.findByUsername(username);
    }

    private Map<String,String> buildHeaders(){
        Map<String,String> headers = new HashMap<>();

        headers.put(AUTHORIZATION, AUTHORIZATION_VALUE);
        headers.put(DEVICE_ID, DEVICE_ID_VALUE);
        headers.put(DEVICE_OS, DEVICE_OS_ID);
        headers.put(VERSION, VERSION_ID);
        headers.put(CLIENT_VERSION, CLIENT_VERSION_ID);
        headers.put(HOST, HOST_ID);
        headers.put(USER_AGENT, USER_AGENT_ID);

        return headers;
    }
}