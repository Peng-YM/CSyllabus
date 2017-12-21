import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import { MyConfiguration } from "./server.configuration";
import { Observable } from "rxjs/Observable";
import { Course } from "./Models/course";

@Injectable()
export class CourseService {
  private courseUrl = `${MyConfiguration.host}/api/course`;
  constructor(
    private http: HttpClient
  ) { }

  getCourse(id: number): Observable<any> {
    const url = `${this.courseUrl}/${id}`;
    return this.http.get<Course>(url);
  }

  getCourseList(): Observable<any>{
    let course_id_list: number[];
    return this.http.get(this.courseUrl);
  }

  addCourse(course: Course): Observable<any>{
    return this.http.post<Course>(this.courseUrl, course, MyConfiguration.httpOptions);
  }

  updateCourse(course: Course): Observable<any> {
    return this.http.put(this.courseUrl, course, MyConfiguration.httpOptions);
  }

  deleteCourse(id: number): Observable<any>{
    const url = `${this.courseUrl}/${id}`;
    return this.http.delete(url);
  }

  // TODO: getSyllabus
  // getSyllabus(id: number): File {
  //   const url = `${this.courseUrl}/${id}/syllabus`;
  //   return this.http.get(url);
  // }

  // TODO:addSyllabus
  // addSyllabus(id: number ): Observable<any> {
  //   const url = `${this.courseUrl}/${id}/syllabus`;
  //   return this.http.post()
  // }


}
