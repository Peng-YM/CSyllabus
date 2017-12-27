import {Component, Input, OnInit} from '@angular/core';
import {User} from "../Models/user";
import {LoginService} from "../login.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  @Input() user: User;
  private confirmPassword;
  private errorMessage;
  constructor(
    private loginService: LoginService
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
          }
        );
    }
  }
}
