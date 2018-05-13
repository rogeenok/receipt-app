package com.netcracker.checkapp.server.model.place;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "places")
@Data
public class Place {

    @Id
    private String id;
    private String name;
    private String address;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Coords coords;
    private Double rating;
    private Integer numOfChecks;
}
