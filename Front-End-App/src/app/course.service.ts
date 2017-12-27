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

  getMultipleCourses(course_ids: number[]): Observable<any> {
    const url = `${this.courseUrl}/multi`;
    return this.http.post(url, {"course_ids": course_ids});
  }

  getCourseList(): Observable<any>{
    let course_id_list: number[];
    return this.http.get(this.courseUrl);
  }

  addCourse(course: Course): Observable<any>{
    return this.http.post<Course>(this.courseUrl, course, MyConfiguration.httpOptions);
  }

  updateCourse(course: Course): Observable<any> {
    const url = `${this.courseUrl}/${course.courseid}`;
    return this.http.put(url, course, MyConfiguration.httpOptions);
  }

  deleteCourse(id: number): Observable<any>{
    const url = `${this.courseUrl}/${id}`;
    return this.http.delete(url);
  }

  getSyllabus(id: number): Observable<any> {
    const url = `${this.courseUrl}/${id}/syllabus`;
    return this.http.get(url);
  }

  // TODO: prerequisite course
  getPrerequisite(): Observable<any> {
    const url = ``;
    return this.http.get(url);
  }

  addPrerequisite(course_ids: number[]): Observable<any> {
    const url = ``;
    return this.http.post(url, {"course_ids": course_ids}, MyConfiguration.httpOptions);
  }
}
