import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { School } from './school';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders(
    {
      'Content-type': 'application/json'
    })
};

@Injectable()
export class SchoolService {
  private schoolUrl = 'http://CSyllabus.org/api/school';
  constructor(
    private http: HttpClient
  ) { }
  /** GET school id list from the server */
  getSchoolIdList(): Observable<number[]> {
    return this.http.get<number[]>(this.schoolUrl)
      .pipe(
        catchError(this.handleError<any>('getSchoolIdList'))
      );
  }
  /** GET school by id, Will 404 if id not found */
  getSchool(id: number): Observable<School> {
    const url = `${this.schoolUrl}/${id}`;
    return this.http.get<School>(url).pipe(
      catchError(this.handleError<School>(`getSchool id=${id}`))
    );
  }
  /**POST: add a new school to the server */
  addSchool(school: School): Observable<School> {
    return this.http.post<School>(this.schoolUrl, School, httpOptions).pipe(
      catchError(this.handleError<School>('addSchool'))
    );
  }

  /**PUT: update the school on the server*/
  updateSchool(school: School): Observable<any> {
    const url = `${this.schoolUrl}/${school.school_id}`;
    return this.http.put(url, School, httpOptions).pipe(
      catchError(this.handleError<any>('updateSchool'))
    );
  }
  /** DELETE: delete the school from the server */
  deleteSchool(school_id: number): Observable<School> {
    const url = `${this.schoolUrl}/${school_id}`;
    return this.http.delete<School>(url).pipe(
      catchError(this.handleError<School>(`deleteSchool: id=${school_id}`))
    );
  }
  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
