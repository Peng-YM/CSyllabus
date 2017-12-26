import { Component, OnInit } from '@angular/core';
import {User} from "../Models/user";
import {UserService} from "../user.service";
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {MyConfiguration} from "../server.configuration";
import {SchoolService} from "../school.service";
import {School} from "../Models/school";
import {LoginService} from "../login.service";
import {Course} from "../Models/course";
import {CourseService} from "../course.service";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})

export class UserDetailComponent implements OnInit {
  user: User;
  userSchool: School;
  private userUrl: string = `${MyConfiguration.host}/api/user`;
  // TODO: get favourite
  favouriteSchools: School[] = [];
  favouriteCourses: Course[] = [];

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private cookieService: CookieService,
    private schoolService: SchoolService,
    private courseService: CourseService,
    private loginService: LoginService
  ) { }
  ngOnInit() {
    this.getCurrentUser();
    this.getFavourites();
  }

  getCurrentUser(): void {
    const id = this.userService.getUserId();
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

  getFavourites(): void{
    this.userService.getFavourites()
      .subscribe(
        data => {
          this.schoolService.getMultiSchools(data['school_ids'])
            .subscribe(
              schools => {this.favouriteSchools = schools.slice();}
            );
          this.courseService.getMultipleCourses(data['course_ids'])
            .subscribe(
              courses => {this.favouriteCourses = courses.slice();}
            );
        }
      );
  }

  logout(): void {
    this.loginService.logout();
  }
}
