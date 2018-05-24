package com.netcracker.checkapp.server.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ShopStats {

    @Id
    private String id;
    private Integer min;
    private Integer max;
    private Double avg;
    private Integer sum;
}
