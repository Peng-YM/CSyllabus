import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {User} from "./Models/user";
import {HttpClient} from "@angular/common/http";
import {MyConfiguration} from "./server.configuration";
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class UserService {
  private userUrl: string = `${MyConfiguration.host}/api/user`;
  constructor(
    private http: HttpClient,
    private cookieService: CookieService
  ) { }

  getCurrentUser(): Observable<User> {
    let id = +this.cookieService.get(MyConfiguration.COOKIE_NAME);
    const url = `${this.userUrl}/${id}`;
    return this.http.get<User>(url);
  }

}
