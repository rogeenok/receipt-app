import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Coords} from "../../placeData/coords";
import {GeolocationService} from "../../../services/geolocation/geolocation.service";
import {ShortPlace} from "../../placeData/short-place";


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
  providers: [GeolocationService]
})
export class MapComponent implements OnInit {


  lat: number = 59.929428;
  lng: number = 30.362019;
  @Output() coords: EventEmitter<Coords>;

  @Input() radius: number = 0;

  @Input() shortPlaces: ShortPlace[];

  marker: Coords;

  constructor(private geolocationService: GeolocationService) {
    this.coords = new EventEmitter<Coords>();
    this.marker = new Coords();
  }

  ngOnInit() {
    let geoPoint = localStorage.getItem("coords");
    if (geoPoint != "{}" && geoPoint != null) {
      this.marker = JSON.parse(geoPoint);
      this.coords.emit(this.marker);
    }
    else {

      if ("geolocation" in navigator) {
        this.geolocationService.getLocation()
          .subscribe((position) => {
            this.marker.longitude=position.coords.longitude;
            this.marker.latitude=position.coords.latitude;
            let point = new Coords(position.coords.latitude,position.coords.longitude);
            this.coords.emit(point);
          });
      } else {
        let point = new Coords(this.lat,this.lng);
        this.marker.longitude=this.lng;
        this.marker.latitude=this.lat;
        this.coords.emit(point);
      }
    }
  }

  setMarkerEvent(coord) {
    this.marker.longitude = coord.lng;
    this.marker.latitude = coord.lat;
    this.coords.emit(this.marker);
  }

}
