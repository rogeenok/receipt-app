import {Component, EventEmitter, OnInit, Output} from '@angular/core';

import {ShortPlace} from "../../../placeData/short-place";
import {Coords} from "../../../placeData/coords";
import {GetCheckData} from "../../../checkData/get-check-data";
import {CheckService} from "../../../../services/checkService/check.service";

@Component({
  selector: 'app-receipt-list-map',
  templateUrl: './receipt-list-map.component.html',
  styleUrls: ['./receipt-list-map.component.css'],
  providers: [CheckService]
})
export class ReceiptListMapComponent implements OnInit {

  radius: number;
  getCheckPlaces: ShortPlace[];
  receipts: GetCheckData[];
  @Output() getReceipts: EventEmitter<GetCheckData[]>;
  @Output() numberOfSelectedReceipts: EventEmitter<number>;
  countSelected: number;
  coords: Coords;
  url = '/api/receipts/places';
  urlPlace = '/api/receipts';

  constructor(private сheckService: CheckService) {
    this.coords = new Coords();
    this.getReceipts = new EventEmitter<GetCheckData[]>();
    this.numberOfSelectedReceipts = new EventEmitter<number>();
    this.countSelected = 0;
  }

  ngOnInit() {
    let rad = localStorage.getItem("radius");
    if (rad != null)
      this.radius = JSON.parse(rad);
    else this.radius = 1000;
    localStorage.setItem("radius", this.radius.toString());
  }

  getCoords(event: Coords) {
    this.coords = event;
    if (event != null && event != {}) {
      localStorage.setItem("coords", JSON.stringify(this.coords));
      this.getPlaces();
    }
  }

  checkPlace(place: ShortPlace) {
    let count=0;
    this.receipts.forEach(data => {
        if (data.shortPlace.id == place.id) {
          data.shortPlace.selected = !data.shortPlace.selected;
          if (data.shortPlace.selected) count++;
        }
      }
    );
    if (count) this.countSelected++;
    else this.countSelected--;
    this.numberOfSelectedReceipts.emit(this.countSelected);
  }


  getPlaces() {
    const params = new URLSearchParams();
    params.set('longitude', this.coords.longitude.toString());
    params.set('latitude', this.coords.latitude.toString());
    params.set('radius', (this.radius / 1000).toString());
    localStorage.setItem("radius", this.radius.toString());
    this.сheckService.getChecks(this.url, params.toString())
      .subscribe((data) => {
        this.getCheckPlaces = [];
        this.receipts = data;
        let places: Map<String, ShortPlace> = new Map();
        data.forEach(obj => {
          places.set(obj.shortPlace.id, obj.shortPlace);
        });
        this.getCheckPlaces = Array.from(places.values());
        this.numberOfSelectedReceipts.emit(0);
        this.getReceipts.emit(data);
      });
  }


}
