import {Injectable} from '@angular/core';
import {Headers, Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {Stats} from "../../user-operations/statsData/stats";


@Injectable()
export class StatsService {

  url = '/api/stats';

  constructor(private http: Http) { }

  getChecks(params: any) {
    return this.http.get(this.url, {params})
      .map(resp => resp.json() as Stats)
      .catch((error: any) => Observable.throw(error));
  }

}
