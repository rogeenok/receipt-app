package com.netcracker.checkapp.server.model;

import com.netcracker.checkapp.server.model.place.ShortPlace;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Fiscal Drive Number and Short Place document class
 */
@Document(collection = "fdsp")
@Data
public class FDSP {

    @Id
    private String id;
    private String fiscalDriveNumber;
    private ShortPlace shortPlace;
}
