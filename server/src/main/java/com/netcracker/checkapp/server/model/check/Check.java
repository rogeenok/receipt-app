package com.netcracker.checkapp.server.model.check;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.netcracker.checkapp.server.model.place.ShortPlace;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "checks")
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Check implements Serializable {

    @Id
    private String id;
    private String fiscalDocumentNumber;
    private String fiscalDriveNumber;
    private String fiscalSign;
    private BigDecimal nds10;
    private BigDecimal nds18;
    private BigDecimal totalSum;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateTime;
    private List<Item> items;
    private String username;
    private ShortPlace shortPlace;
}