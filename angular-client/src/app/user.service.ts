import {User} from './model/user';
import {Injectable} from '@angular/core';
import {Http, RequestOptions, URLSearchParams, Headers} from '@angular/http';

@Injectable()
export class UserService {

  private userByIdUrl = 'http://localhost:4200/eventfinder/rest/userById/';
  private saveUrl = 'http://localhost:4200/eventfinder/rest/subscribe_advertiser';
  private forgetUrl = 'http://localhost:4200/eventfinder/rest/unsubscribe_advertiser';

  constructor(private http: Http) {}

  getUserById(id: string): Promise<User> {
    return this.http.get(this.userByIdUrl + id)
      .toPromise()
      .then(response => response.json() as User)
      .catch(this.handleError);
  }

  public saveAdvertiser(advertiser: User, user: User) {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    user.savedAdvertisers.push(advertiser);

    return this.http.post(this.saveUrl, advertiser.id, options).toPromise()
      .catch(this.handleError);
  }

  public forgetAdvertiser(advertiser: User, user: User) {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});

    for (let i = 0; i < user.savedAdvertisers.length; i++) {
      if (user.savedAdvertisers[i].id === advertiser.id) {
        user.savedAdvertisers.splice(i, 1);
      }
    }

    return this.http.post(this.forgetUrl, advertiser.id, options).toPromise()
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

}
