import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

@Injectable()
export class UserServiceService {

 constructor(private http: Http) {}

  private eventsUrl = 'http://localhost:4200/eventfinder/rest/events/search';

}
