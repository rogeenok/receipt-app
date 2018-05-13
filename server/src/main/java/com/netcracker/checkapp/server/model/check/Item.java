package com.netcracker.checkapp.server.model.check;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Item {
    private BigDecimal price;
    @JsonProperty(required = false)
    private BigDecimal ndsSum;
    @JsonProperty(required = false)
    private BigDecimal nds10;
    @JsonProperty(required = false)
    private BigDecimal nds18;
    private Double quantity;
    private String name;

    public Item() {
        this.ndsSum = new BigDecimal(0);
        this.nds10 = new BigDecimal(0);
        this.nds18 = new BigDecimal(0);
    }
}