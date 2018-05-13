import { Injectable } from '@angular/core';
import {Place} from "../../user-operations/placeData/place";
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Injectable()
export class SharedPlaceService {

  newPlace: Place;
  sharedPlace = new BehaviorSubject<Place>(null);
  constructor() { }

  setPlace(place:Place){
    this.newPlace=place;
    this.sharedPlace.next(this.newPlace);
  }
}
