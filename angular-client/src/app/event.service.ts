import {Injectable} from '@angular/core';
import {Http, RequestOptions, URLSearchParams, Headers} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Event} from './event';


@Injectable()
export class EventService {

  id: number;

  constructor(private http: Http) {}

  private eventsUrl = 'http://localhost:4200/eventfinder/rest/events/search';
  private eventUrl = 'http://localhost:4200/eventfinder/rest/details';
  private typesUrl = 'http://localhost:4200/eventfinder/rest/types';

  getEvents(): Promise<Event[]> {
    return this.http.get(this.eventsUrl)
      .toPromise()
      .then(response => response.json() as Event[])
      .catch(this.handleError);
  }

  getTypes(): Promise<string[]> {
    return this.http.get(this.typesUrl)
      .toPromise()
      .then(response => response.json() as Array<string>[])
      .catch(this.handleError);
  }

  getEventById(id: string): Promise<Event> {
    const cpParams = new URLSearchParams();
    const cpHeaders = new Headers({'Content-Type': 'application/json'});
    cpParams.set('id', id);
    const options = new RequestOptions({params: cpParams, headers: cpHeaders});
    return this.http.get(this.eventUrl, options)
      .toPromise()
      .then(response => response.json() as Event)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

}
