package com.netcracker.checkapp.server.service.statservice;

import com.netcracker.checkapp.server.model.DateStats;
import com.netcracker.checkapp.server.model.check.Check;
import com.netcracker.checkapp.server.persistance.CheckRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements StatService {

    private CheckRepository checkRepository;

    StatServiceImpl(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @Override
    public Map<String, Object> getStats() {
        Map<String, Object> map = new HashMap<>();
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Check> userChecks = checkRepository.findByUsername(principal.getUsername());

        if (userChecks.size() == 0) {
            map.put("totalChecks","0");
            map.put("minTotalSum","0.00");
            map.put("maxTotalSum","0.00");
            map.put("medTotalSum","0.00");
        } else {

            userChecks.sort(Comparator.comparing(Check::getTotalSum));

            map.put("totalChecks", userChecks.size());
            map.put("minTotalSum", userChecks.get(0).getTotalSum());
            map.put("maxTotalSum", userChecks.get(userChecks.size() - 1).getTotalSum());

            // use that stream.reduce methods if necessary in future
//        Check maxCheck = userChecks.stream().reduce((c1,c2) -> c1.getTotalSum().compareTo(c2.getTotalSum()) == 1 ? c1 : c2).orElse(null);
//        map.put("maxTotalSum",String.valueOf(maxCheck.getTotalSum()));
//        Check minCheck = userChecks.stream().reduce((c1,c2) -> c1.getTotalSum().compareTo(c2.getTotalSum()) == -1 ? c1 : c2).orElse(null);
//        map.put("minTotalSum",String.valueOf(minCheck.getTotalSum()));

//            double median = (userChecks.size() % 2 == 1)
//                    ? userChecks.get(userChecks.size() / 2).getTotalSum().doubleValue()
//                    : (userChecks.get(userChecks.size() / 2 - 1).getTotalSum().add(userChecks.get(userChecks.size() / 2).getTotalSum()).doubleValue()) / 2;
//            map.put("medTotalSum", String.valueOf(median));

            Double avg =
                    userChecks.stream().map(Check::getTotalSum).reduce((ts1,ts2) -> ts1 + ts2).orElse(new Double(0)) / userChecks.size();
            map.put("avgTotalSum",avg);
        }

        map.put("shopStats",checkRepository.getShopStats());

//        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.ENGLISH);
//        String[] months = dateFormatSymbols.getShortMonths();
        String[] months = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
        List<DateStats> dateStats = checkRepository.getDateStats();
        dateStats = dateStats.stream().sorted(Comparator.comparing(DateStats::getId)).collect(Collectors.toList());
        for (DateStats dateStats1: dateStats) {
            dateStats1.setId(months[Integer.valueOf(dateStats1.getId())-1]);
        }

        map.put("dateStats", dateStats);

        return map;
    }
}