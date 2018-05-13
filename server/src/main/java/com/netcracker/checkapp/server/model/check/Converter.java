package com.netcracker.checkapp.server.model.check;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Converter {
    public static Check fromNalogRuCheckToCheck(NalogRuCheck nalogRuCheck) {
        Check check = new Check();
        List<Item> items = nalogRuCheck.getItems();
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        check.setFiscalDocumentNumber(nalogRuCheck.getFiscalDocumentNumber());
        check.setFiscalDriveNumber(nalogRuCheck.getFiscalDriveNumber());
        check.setFiscalSign(nalogRuCheck.getFiscalSign());
        check.setNds10(new BigDecimal(nalogRuCheck.getNds10()).divide(new BigDecimal(100)));
        check.setNds18(new BigDecimal(nalogRuCheck.getNds18()).divide(new BigDecimal(100)));
        check.setTotalSum(new BigDecimal(nalogRuCheck.getTotalSum()).divide(new BigDecimal(100)));
        check.setDateTime(LocalDateTime.parse(nalogRuCheck.getDateTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        items.forEach(item -> {
            item.setPrice(item.getPrice().divide(new BigDecimal(100)));
            item.setNds10(item.getNds10().divide(new BigDecimal(100)));
            item.setNds18(item.getNds18().divide(new BigDecimal(100)));
            item.setNdsSum(item.getNds10().equals(new BigDecimal(0))
                    ? item.getNds18() : item.getNds10());
        });
        check.setItems(items);
        check.setUsername(principal.getUsername());

        return check;
    }
}
