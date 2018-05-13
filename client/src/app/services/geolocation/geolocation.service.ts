import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {Coords} from "../../user-operations/placeData/coords";


@Injectable()
export class GeolocationService {

  constructor() {
  }


  public getLocation(): Observable<any> {
    return Observable.create(observer => navigator.geolocation.getCurrentPosition(
      (position) => {
        observer.next(position);
        observer.complete();
      }))
  }
}
