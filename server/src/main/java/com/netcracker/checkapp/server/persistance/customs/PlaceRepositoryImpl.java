package com.netcracker.checkapp.server.persistance.customs;

import com.mongodb.WriteResult;
import com.netcracker.checkapp.server.model.place.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public int updatePlace(String id) {

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("numOfChecks",1);

        WriteResult result = mongoTemplate.updateFirst(query,update,Place.class,"places");

        if (result != null)
            return result.getN();
        else
            return 0;
    }
}
