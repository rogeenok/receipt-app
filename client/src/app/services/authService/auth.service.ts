import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {HttpService} from "../httpService/http.service";
import {Observable} from "rxjs/Observable";
import {Http, Response, Headers} from "@angular/http";

@Injectable()
export class AuthService {
  au = new BehaviorSubject<boolean>(this.hasToken());
  data = this.au.asObservable();
  providers: [HttpService];

  constructor(private httpService: HttpService,
              private http: Http) { }

  logout(url: string) {
    return this.http.post(url, {})
      .map((res: Response) => {
        this.change();
        sessionStorage.removeItem('token');
        return res.totalBytes > 0 ? res.json() : null;
      })
      .catch((error: any) => Observable.throw(error));
  }

  login(url: string, params: string) {
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    return this.http.post(url, params, {headers: headers})
      .map((res: Response) => {
        this.change();
        sessionStorage.setItem('token', 'on');
        return res.totalBytes > 0 ? res.json() : null;
      })
      .catch((error: any) => Observable.throw(error));
  }

  change() {
    this.au.next(!this.au.value);
  }

  check() {
    this.httpService.getData('/api/users/current', null)
      .subscribe(resp => {
        if (resp.json().username != 'anonymousUser') {
          this.au.next(true);
        }
      });
  }

  private hasToken() : boolean {
    return !!sessionStorage.getItem('token');
  }
}
