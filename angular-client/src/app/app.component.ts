import {EventService} from './event.service';
import {SecurityService} from './security.service';
import {Component} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-root',
  providers: [EventService],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Eventfinder';


  myData: Array<any>;

  constructor(private securityService: SecurityService) {

    securityService.init();

  }

}
