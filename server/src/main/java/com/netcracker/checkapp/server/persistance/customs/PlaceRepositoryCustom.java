package com.netcracker.checkapp.server.persistance.customs;

public interface PlaceRepositoryCustom {

    /*
        this method increments the field 'numOfChecks' in Place object
        and it should be implemented customly
     */
    int updatePlace(String id);
}
