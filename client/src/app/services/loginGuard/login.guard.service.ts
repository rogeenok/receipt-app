import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {AuthService} from "../authService/auth.service";
import 'rxjs/add/operator/delay';

@Injectable()
export class LoginGuardService implements CanActivate {
  authenticated: boolean;

  constructor(private auth: AuthService,
              private router: Router) {
    this.auth.data.subscribe(value => {
      this.authenticated = value;
    });
  }

  canActivate() : Observable<boolean> | boolean {
    if (!this.checkIfLoggedIn()) {
      this.router.navigate(['/']);
    }
    return this.checkIfLoggedIn();
  }

  private checkIfLoggedIn(): Observable<boolean> | boolean {
    return this.authenticated;
  }

}
