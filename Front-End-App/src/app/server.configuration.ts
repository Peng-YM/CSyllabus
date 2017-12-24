import {HttpHeaders} from "@angular/common/http";

export class MyConfiguration {
  static host: string = "http://localhost:8888";
  static httpOptions =  {
    headers: new HttpHeaders(
      {
        'Content-Type': 'application/json',
        'Charset': 'utf-8'
      })
  };
  static COOKIE_NAME: string = "6fZaZcfhM%KNRaXa6612";
}
