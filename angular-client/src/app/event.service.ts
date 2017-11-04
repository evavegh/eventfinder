import {Injectable} from '@angular/core';
import {Http, RequestOptions, URLSearchParams, Headers} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Event} from './model/event';
import {SecurityService} from './security.service';
import {User} from './model/user';


@Injectable()
export class EventService {

  id: number;

  constructor(private http: Http) {}

  private eventsUrl = 'http://localhost:4200/eventfinder/rest/events/search';
  private eventUrl = 'http://localhost:4200/eventfinder/rest/details';
  private typesUrl = 'http://localhost:4200/eventfinder/rest/types';
  private saveUrl = 'http://localhost:4200/eventfinder/rest/subscribe_event';
  private forgetUrl = 'http://localhost:4200/eventfinder/rest/unsubscribe_event';

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

  public saveEvent(event: Event, user: User) {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    user.savedEvents.push(event);

    return this.http.post(this.saveUrl, event.id, options).toPromise()
      .catch(this.handleError);
  }

  public forgetEvent(event: Event, user: User) {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});

    for (let i = 0; i < user.savedEvents.length; i++) {
      if (user.savedEvents[i].id === event.id) {
        user.savedEvents.splice(i, 1);
      }
    }

    return this.http.post(this.forgetUrl, event.id, options).toPromise()
      .catch(this.handleError);
  }

  public getlatlng(address) {
    return this.http.get('https://maps.googleapis.com/maps/api/geocode/json?address=' + address).toPromise()
      .catch(this.handleError);
  }
}
