import {Component, Input, OnInit} from '@angular/core';
import {User} from "../Models/user";
import {LoginService} from "../login.service";
import {MyConfiguration} from "../server.configuration";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  @Input() user: User;
  confirmPassword: string;
  errorMessage: string;
  constructor(
    private loginService: LoginService,
    private cookieService: CookieService
  ) { }

  ngOnInit() {
    this.user = new User();
  }

  registration(): void {
    console.log(JSON.stringify(this.user));

    if(this.confirmPassword !== this.user.password){
      this.errorMessage = "Two input password must be consistent！";
    }
    else{
      this.loginService.registration(this.user)
        .subscribe(
          user => {this.user = user},
          err => {console.log(err)},
          () => {
            document.location.assign("/dashboard");
            this.cookieService.set(MyConfiguration.COOKIE_NAME, `${this.user.id}`);
          }
        );
    }
  }
}
