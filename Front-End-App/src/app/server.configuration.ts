import {HttpHeaders} from "@angular/common/http";

export class MyConfiguration {
  static host: string = "http://69.171.71.251:8888";
  static httpOptions =  {
    headers: new HttpHeaders(
      {
        'Content-Type': 'application/json',
        'Charset': 'utf-8'
      })
  };
  static COOKIE_NAME: string = "6fZaZcfhM%KNRaXa6612";
}
