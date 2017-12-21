import {HttpHeaders} from "@angular/common/http";

export class MyConfiguration {
  static host: string = "http://localhost:12000";
  static httpOptions =  {
    headers: new HttpHeaders(
      {
        'Content-Type': 'application/json',
        'Charset': 'utf-8'
      })
  };
}
