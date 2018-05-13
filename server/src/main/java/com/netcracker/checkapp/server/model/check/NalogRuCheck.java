package com.netcracker.checkapp.server.model.check;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NalogRuCheck {
    private static final String DEFAULT_NDS18 = "0";
    private static final String DEFAULT_NDS10 = "0";

    private String fiscalDocumentNumber;
    private String fiscalDriveNumber;
    private String fiscalSign;
    @JsonProperty(required = false)
    private String nds10;
    @JsonProperty(required = false)
    private String nds18;
    private String totalSum;
    private String dateTime;
    private List<Item> items;

    public NalogRuCheck() {
        this.nds18 = DEFAULT_NDS18;
        this.nds10 = DEFAULT_NDS10;
    }
}

