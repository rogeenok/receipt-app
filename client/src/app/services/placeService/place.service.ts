import {Injectable} from '@angular/core';
import {Headers, Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {Place} from "../../user-operations/placeData/place";


@Injectable()
export class PlaceService {

constructor(private http: Http) { }

  createPlace(params: string, url: string) {
    let headers = new Headers({'Content-Type': 'application/json'});
    return this.http.post(url, params, {headers: headers})
      .catch((error: any) => Observable.throw(error));
  }

  getPlaces(url: string, params: any) {
    return this.http.get(url, {params})
      .map(resp => resp.json() as Place[]);
  }

}
