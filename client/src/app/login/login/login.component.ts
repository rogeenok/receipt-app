import { Component, OnInit } from '@angular/core';
import {FullUser} from "../../user-operations/checkData/full-user";
import {HttpService} from "../../services/httpService/http.service";
import {Router} from "@angular/router";
import {AuthService} from "../../services/authService/auth.service";
import {MessageProcessingService} from "../../services/messageService/message.processing.service";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [HttpService]
})
export class LoginComponent implements OnInit {
  fullUser: FullUser = new FullUser();
  done = false;
  loginUrl = '/login';
  registerUrl = '/register';

  constructor(private httpService: HttpService,
              private router: Router,
              private auth: AuthService,
              private proc: MessageProcessingService) { }

  submit(info) {
    const params = new URLSearchParams();
    params.set('username', info.login);
    params.set('password', info.pwd);
    this.auth.login(this.loginUrl, params.toString())
    .subscribe((data) => {
      this.router.navigate(['/user-operations']);
    },
      error => {
        this.showErrorMessage(error,"Произошла ошибка во время входа в систему");
      });
  }

  signup(fullUser) {
    this.httpService.postBody(fullUser, this.registerUrl)
      .subscribe((data) => {
          this.submit(fullUser);
        },
        error => {
          this.showErrorMessage(error,"Произошла ошибка во время регистрации");
        });
  }

  showErrorMessage(error: any, message : string) {
    if (error.status == 500) {
      this.proc.showMessage(message);
    } else {
      this.proc.showMessage("Соединение с сервером потеряно");
    }
  }

  ngOnInit() {
  }

}
