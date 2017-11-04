import {Location} from './model/location';
import {User} from './model/user';
import {Injectable} from '@angular/core';
import {Http, RequestOptions, URLSearchParams, Headers} from '@angular/http';

@Injectable()
export class LocationService {

  private saveUrl = 'http://localhost:4200/eventfinder/rest/subscribe_location';
  private forgetUrl = 'http://localhost:4200/eventfinder/rest/unsubscribe_location';

  private locationByEventIdUrl = 'http://localhost:4200/eventfinder/rest/locationByEventId/';

  constructor(private http: Http) {}

  getLocationById(id: string): Promise<Location> {
    return this.http.get(this.locationByEventIdUrl + id)
      .toPromise()
      .then(response => response.json() as Location)
      .catch(this.handleError);
  }

  public saveLocation(location: Location, user: User) {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    user.savedLocations.push(location);

    return this.http.post(this.saveUrl, location.id, options).toPromise()
      .catch(this.handleError);
  }

  public forgetLocation(location: Location, user: User) {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});

    for (let i = 0; i < user.savedLocations.length; i++) {
      if (user.savedLocations[i].id === location.id) {
        user.savedLocations.splice(i, 1);
      }
    }

    return this.http.post(this.forgetUrl, location.id, options).toPromise()
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

}
