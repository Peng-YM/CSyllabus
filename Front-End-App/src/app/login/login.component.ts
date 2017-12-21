import {Component, Input, OnInit} from '@angular/core';
import { User } from "../Models/user";
import { LoginService } from "../login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @Input() user: User;
  private errorMessage;
  constructor(
    private loginService: LoginService
  ) { }

  ngOnInit() {
    this.user = new User();
  }

  login(): void {
    console.log(`User: ${this.user.name} is trying to login`);
    this.loginService.login(this.user)
      .subscribe(
        () => {},
        err => {
          this.errorMessage = err;
          console.log(this.errorMessage);
        },
        ()=> {
          this.errorMessage = null;
          // TODO: Set Cookie
          // TODO: Set conditional route to dashboard
        }
        );
  }


  reset(): void {
    this.user = new User();
  }

}
