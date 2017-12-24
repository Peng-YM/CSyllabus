import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
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
  addSyllabus(id: number, inputValue: any): void {
    const url = `${this.courseUrl}/${id}/syllabus`;
    let httpOptions =  {
      headers: new HttpHeaders(
        {
          'Content-Type': 'multipart/form-data',
          'Charset': 'utf-8'
        })
    };
    let formData = new FormData();
    formData.append("name", "Name");
    formData.append("file", inputValue.files[0]);
    this.http.post(
      url, formData, httpOptions
    );
  }


}
