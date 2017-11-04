import {User} from './model/user';
import {Injectable} from '@angular/core';
import {Http, RequestOptions, URLSearchParams, Headers} from '@angular/http';

@Injectable()
export class SecurityService {

  private currentUserUrl = 'http://localhost:4200/eventfinder/rest/currentUser';
  private registrationUrl = 'http://localhost:4200/eventfinder/registration';
  private user: User;

  constructor(private http: Http) {
  }

  public init() {
    this.getCurrentUser().then(user => this.user = user);
  }

  private getCurrentUser(): Promise<User> {
    return this.http.get(this.currentUserUrl)
      .toPromise()
      .then(response => this.parseUser(response.text()))
      .catch(this.handleError);
  }

  public isAuthenticated(): boolean {
    return this.user != null;
  }

  public hasRole(role: string): boolean {
    return this.isAuthenticated() && this.user.roles.indexOf(role) >= 0;
  }

  public getUser(): User {
    return this.user;
  }

  private parseUser(responseBody: string): User {
    return responseBody ? JSON.parse(responseBody) as User : null;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  public postUser(user: User) {
    console.log(user);
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.post('http://localhost:4200/eventfinder/settings', user, options).toPromise()
      .then(response => this.parseUser(response.text()))
      .catch(this.handleError);
  }

  public registerUser(user: User) {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.post(this.registrationUrl, user, options).toPromise()
      .then(response => this.parseUser(response.text()))
      .catch(this.handleError);
  }

}
