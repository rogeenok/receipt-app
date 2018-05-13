package com.netcracker.checkapp.server.service.placeservice;


import com.netcracker.checkapp.server.model.place.Coords;
import com.netcracker.checkapp.server.model.place.Place;

import java.util.List;

public interface PlaceService {
    Place incrementPlaceRating(Place place);

    List<Place> getNearPlaces(Coords coords, double radius);
    Place addNewPlace(Place place);
}
