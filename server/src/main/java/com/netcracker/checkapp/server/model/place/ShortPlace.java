package com.netcracker.checkapp.server.model.place;

import lombok.Data;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Data
public class ShortPlace {

    private String id;
    private String name;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Coords coords;
}
