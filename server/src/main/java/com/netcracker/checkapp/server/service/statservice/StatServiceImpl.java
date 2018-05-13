package com.netcracker.checkapp.server.service.statservice;

import com.netcracker.checkapp.server.model.check.Check;
import com.netcracker.checkapp.server.persistance.CheckRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {

    private CheckRepository checkRepository;

    StatServiceImpl(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @Override
    public Map<String, String> getStats() {
        Map<String, String> map = new HashMap<>();
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Check> userChecks = checkRepository.findByUsername(principal.getUsername());
        List<Check> allChecks = checkRepository.findAll();

        if (userChecks.size() == 0) {
            map.put("totalChecks","0");
            map.put("minTotalSum","0.00");
            map.put("maxTotalSum","0.00");
            map.put("medTotalSum","0.00");

            if (allChecks.size() == 0) {
                map.put("totalChecksAll","0");
                map.put("totalSumAll","0.00");
                return map;
            }
        } else {

            userChecks.sort(Comparator.comparing(Check::getTotalSum));

            map.put("totalChecks", String.valueOf(userChecks.size()));
            map.put("minTotalSum", String.valueOf(userChecks.get(0).getTotalSum()));
            map.put("maxTotalSum", String.valueOf(userChecks.get(userChecks.size() - 1).getTotalSum()));

            // use that stream.reduce methods if necessary in future
//        Check maxCheck = userChecks.stream().reduce((c1,c2) -> c1.getTotalSum().compareTo(c2.getTotalSum()) == 1 ? c1 : c2).orElse(null);
//        map.put("maxTotalSum",String.valueOf(maxCheck.getTotalSum()));
//        Check minCheck = userChecks.stream().reduce((c1,c2) -> c1.getTotalSum().compareTo(c2.getTotalSum()) == -1 ? c1 : c2).orElse(null);
//        map.put("minTotalSum",String.valueOf(minCheck.getTotalSum()));

            double median = (userChecks.size() % 2 == 1)
                    ? userChecks.get(userChecks.size() / 2).getTotalSum().doubleValue()
                    : (userChecks.get(userChecks.size() / 2 - 1).getTotalSum().add(userChecks.get(userChecks.size() / 2).getTotalSum()).doubleValue()) / 2;
            map.put("medTotalSum", String.valueOf(median));
        }

        map.put("totalChecksAll",String.valueOf(allChecks.size()));

        BigDecimal sum = allChecks.stream().map(Check::getTotalSum).reduce((ts1,ts2) -> ts1.add(ts2)).orElse(new BigDecimal(0));
        map.put("totalSumAll",String.valueOf(sum));

        return map;
    }
}
