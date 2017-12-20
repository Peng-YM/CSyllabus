import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import { MyConfiguration } from "./server.configuration";
import { Observable } from "rxjs/Observable";
// import { Course } from "./Models/course";

@Injectable()
export class CourseService {
  private courseUrl = `${MyConfiguration.host}/api/course`;
  constructor(
    private http: HttpClient
  ) { }


}
