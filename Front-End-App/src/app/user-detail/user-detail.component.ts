import { Component, OnInit } from '@angular/core';
import {User} from "../Models/user";
import {UserService} from "../user.service";
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {MyConfiguration} from "../server.configuration";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})

export class UserDetailComponent implements OnInit {
  user: User;
  private userUrl: string = "/api/user";
  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private cookieService: CookieService
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
        }
      );
  }
}
