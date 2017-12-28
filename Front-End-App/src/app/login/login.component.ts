import {Component, Input, OnInit} from '@angular/core';
import { User } from "../Models/user";
import { LoginService } from "../login.service";
import { CookieService } from "ngx-cookie-service";
import {MyConfiguration} from "../server.configuration";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @Input() user: User;
  errorMessage:  string;
  constructor(
    private cookieService: CookieService,
    private loginService: LoginService
  ) { }

  ngOnInit() {
    this.user = new User();
  }

  login(): void {
    this.loginService.login(this.user)
      .subscribe(
        user => {this.user = user},
        err => {
          this.errorMessage = err;
          console.log(this.errorMessage);
        },
        ()=> {
          this.errorMessage = null;
          this.setCookie();
          document.location.assign("/dashboard");
        }
        );
  }

  setCookie(): void {
    this.cookieService.set(MyConfiguration.COOKIE_NAME, `${this.user.id}`);
  }


}
