import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Place} from "../../../../placeData/place";
import {Coords} from "../../../../placeData/coords";
import {HttpService} from "../../../../../services/httpService/http.service";
import {PlaceService} from "../../../../../services/placeService/place.service";
import {Subscription} from "rxjs/Subscription";
import {SharedPlaceService} from "../../../../../services/sharedPlace/shared-place.service";

@Component({
  selector: 'app-place-list',
  templateUrl: './place-list.component.html',
  styleUrls: ['./place-list.component.css']
})
export class PlaceListComponent implements OnInit, OnDestroy {

  radius: number;
  places: Place[];
  url = '/api/places';
  coords: Coords;
  @Output() currentCoords: EventEmitter<Coords>;
  @Output() currentPlace: EventEmitter<Place>;
  subscription: Subscription;
  selectedOption: string;
  newPlaceAdded: boolean;
  newPlace: Place;
  coordsAdded: boolean;

  constructor(private placeService: PlaceService,
              private sharedService: SharedPlaceService) {
    this.coords = new Coords();
    this.currentCoords = new EventEmitter<Coords>();
    this.currentPlace = new EventEmitter<Place>();
  }

  ngOnInit() {
    this.radius = 200;
    let newPlace = this.sharedService.sharedPlace
      .subscribe((data) => {
        this.newPlace = data;
        if (data != null) {
          this.newPlaceAdded = true;
          this.selectedOption = data.id;
        } else {
          this.newPlaceAdded = false;
          if (this.coordsAdded)
            this.getPlaces();
        }
      });
    this.subscription = newPlace;
  }

  getCoords(event) {
    this.coords = event;
    this.getPlaces();
    this.coordsAdded = true;
    this.currentCoords.emit(this.coords);
  }

  getPlaces() {
    const params = new URLSearchParams();
    params.set('longitude', this.coords.longitude.toString());
    params.set('latitude', this.coords.latitude.toString());
    this.placeService.getPlaces(this.url, params.toString())
      .subscribe((data) => {
        this.places = data;
      });
  }

  getCurrentPlace(place) {
    if (!this.newPlaceAdded)
      this.currentPlace.emit(place);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setOption(option: string) {
    if (!this.newPlaceAdded)
      this.selectedOption = option;
  }

}
