import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { School } from './Models/school';
import { MyConfiguration } from "./server.configuration";
import {Course} from "./Models/course";
import {map} from "rxjs/operators";


@Injectable()
export class SchoolService {
  private schoolUrl = `${MyConfiguration.host}/api/school`;
  constructor(
    private http: HttpClient
  ) {}
  /** GET school id list from the server */
  getSchoolIdList(): Observable<any>{
    return this.http.get<any>(this.schoolUrl);
  }
  /** GET school by id, Will 404 if id not found */
  getSchool(id: number): Observable<School> {
    const url = `${this.schoolUrl}/${id}`;
    return this.http.get<School>(url);
  }

  getMultiSchools(school_ids: number[]): Observable<any>{
    const url = `${this.schoolUrl}/multi`;
    return this.http.post(url, {"school_ids": school_ids}, MyConfiguration.httpOptions);
  }
  /**POST: add a new school to the server */
  addSchool(school: School): Observable<School> {
    return this.http.post<School>(this.schoolUrl, school, MyConfiguration.httpOptions);
  }
  /**PUT: update the school on the server*/
  updateSchool(school: School): Observable<any> {
    const url = `${this.schoolUrl}/${school.schoolid}`;
    return this.http.put(url, school, MyConfiguration.httpOptions);
  }
  /** DELETE: delete the school from the server */
  deleteSchool(school_id: number): Observable<any> {
    const url = `${this.schoolUrl}/${school_id}`;
    return this.http.delete(url);
  }
  /**GET: get course id list*/
  getCourseIdList(school_id: number): Observable<any> {
    const url = `${this.schoolUrl}/${school_id}/courses`;
    return this.http.get(url);
  }

  getPrerequisiteCourses(school_id: number, course_id: number): Observable<any>{
    const url = `${this.schoolUrl}/${school_id}/tree`;
    return this.http.get(url).pipe(
      map(
        data => {
          let pre: number[] = [];
            for (let edge of data['edges']){
              if (edge['target'] === course_id){
                pre.push(edge['source']);
              }
            }
          return pre;
        }
      )
    );
  }
}
