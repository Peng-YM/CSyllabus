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

  getUserId(): number {
    return +this.cookieService.get(MyConfiguration.COOKIE_NAME);
  }

  getCurrentUser(): Observable<User> {
    let id = this.getUserId();
    if (id === 0){
      return null;
    }
    const url = `${this.userUrl}/${id}`;
    return this.http.get<User>(url);
  }

  // the following methods assert that user has logged in
  getFavourites(): Observable<any> {
    let id = this.getUserId();
    const url = `${this.userUrl}/star/${id}`;
    return this.http.get(url);
  }

  setFavourites(favourites: any): void {
    let id = this.getUserId();
    const url = `${this.userUrl}/star/${id}`;
    this.http.post(url, favourites, MyConfiguration.httpOptions).subscribe();
  }

  addFavouriteCourse(courseid: number): void{
    this.getFavourites()
      .subscribe(
        data => {
          let temp = data['course_ids'];
          const index = temp.indexOf(courseid);
          if(index === -1){
            temp.push(courseid);
            this.setFavourites({'course_ids': temp});
          }
        });
  }

  unfavouriteCourse(courseid: number): void{
    this.getFavourites()
      .subscribe(
        data => {
          let temp = data['course_ids'];
          const index = temp.indexOf(courseid);

          if(index > -1){
            temp = temp.splice(courseid, 1);
            this.setFavourites({'course_ids': temp});
          }
        }
      );
  }

  addFavouriteSchool(schoolid: number): void{
    this.getFavourites()
      .subscribe(
        data => {
          let temp = data['school_ids'];
          const index = temp.indexOf(schoolid);
          if(index === -1){
            temp.push(schoolid);
            this.setFavourites({'school_ids': temp});
          }
        });
  }

  unfavouriteSchool(schoolid: number): void{
    this.getFavourites()
      .subscribe(
        data => {
          let temp = data['school_ids'];
          const index = temp.indexOf(schoolid);
          if(index > -1){
            temp = temp.splice(schoolid, 1);
            this.setFavourites({'school_ids': temp});
          }
        }
      );
  }
}
