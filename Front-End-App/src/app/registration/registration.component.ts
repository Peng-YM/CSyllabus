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
    if(this.confirmPassword !== this.user.password){
      this.errorMessage = "Two input password must be consistent！";
    }
    else{
      console.log(`User: ${this.user.name} is trying to register`);
      this.loginService.registration(this.user)
        .subscribe(user => this.user = user);
    }
  }

  reset(): void {
    this.user = new User();
  }
}
