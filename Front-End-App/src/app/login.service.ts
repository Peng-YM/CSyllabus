import { Injectable } from '@angular/core';
import {User} from "./Models/user";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {MyConfiguration} from "./server.configuration";

const loginUrl = `${MyConfiguration.host}/api/login`;
const registrationUrl = `${MyConfiguration.host}/api/registration`;

@Injectable()
export class LoginService {

  constructor(
    private http: HttpClient
  ) { }
  login(user: User): Observable<User> {
    return this.http.post<User>(loginUrl, user, MyConfiguration.httpOptions);
  }
  registration(user: User): Observable<User> {
    return this.http.post<User>(registrationUrl, user, MyConfiguration.httpOptions);
  }
}
