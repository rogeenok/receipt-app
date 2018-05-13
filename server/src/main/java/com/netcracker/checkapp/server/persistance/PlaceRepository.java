package com.netcracker.checkapp.server.persistance;

import com.netcracker.checkapp.server.model.place.Place;
import com.netcracker.checkapp.server.persistance.customs.PlaceRepositoryCustom;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String>, PlaceRepositoryCustom {

    List<Place>  findByCoordsNear(Point coords,Distance distance);
}
