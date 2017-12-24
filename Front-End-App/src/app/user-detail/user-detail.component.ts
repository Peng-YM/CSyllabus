import { Component, OnInit } from '@angular/core';
import {User} from "../Models/user";
import {UserService} from "../user.service";
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {MyConfiguration} from "../server.configuration";
import {SchoolService} from "../school.service";
import {School} from "../Models/school";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})

export class UserDetailComponent implements OnInit {
  user: User;
  userSchool: School;
  private userUrl: string = `${MyConfiguration.host}/api/user`;
  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private cookieService: CookieService,
    private schoolService: SchoolService
  ) { }
  ngOnInit() {
    this.getCurrentUser();
  }

  getCurrentUser(): void {
    const id =  +this.cookieService.get(MyConfiguration.COOKIE_NAME);;
    const url = `${this.userUrl}/${id}`;
    this.http.get<User>(url)
      .subscribe(
        user => {
          this.user = user;
        },
        err => {},
        () => {
          if(this.user.school !== null){
            this.schoolService.getSchool(this.user.school)
              .subscribe(
                school => {
                  this.userSchool = school;
                }
              );
          }

        }
      );
  }
}
