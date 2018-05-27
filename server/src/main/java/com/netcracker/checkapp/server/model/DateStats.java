package com.netcracker.checkapp.server.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class DateStats {

    @Id
    private String id;
    private Integer sum;
}
