package com.netcracker.checkapp.server.service.placeservice;


import com.netcracker.checkapp.server.model.place.Coords;
import com.netcracker.checkapp.server.model.place.Place;
import com.netcracker.checkapp.server.persistance.PlaceRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlaceServiceImpl implements PlaceService {
    PlaceRepository placeRepository;

    PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public Place incrementPlaceRating(Place place) {
        if (placeRepository.updatePlace(place.getId()) != 0)
            return placeRepository.findOne(place.getId());
        else
            return null;
    }

    @Override
    public Place addNewPlace(Place place) {
        if (place.getId() == null)
            return placeRepository.save(place);
        Place localPlace = placeRepository.findOne(place.getId());
        if (localPlace != null)
            localPlace = incrementPlaceRating(place);
        else
            localPlace = placeRepository.save(place);
        return localPlace;
    }


    @Override
    public List<Place> getNearPlaces(Coords coords, double radius) {
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        Point point = new Point(coords.getLatitude(),coords.getLongitude());
        return placeRepository.findByCoordsNear(point,distance);
    }

}
