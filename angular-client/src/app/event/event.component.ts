import {DetailsComponent} from '../details/details.component';
import {EventService} from '../event.service';
import {Component, OnInit, ViewChildren} from '@angular/core';
import {Router} from '@angular/router';
import {NamePipe, LocationPipe, CityPipe, StartDatePipe, EndDatePipe, OnlySavedPipe} from '../SearchPipe';

import {Event} from '../event';
import {SecurityService} from '../security.service';
import {Type} from '../type';
import {User} from '../user';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  events: Event[];
  event: Event;
  types: Type[];

  @ViewChildren(DetailsComponent) panels;

  constructor(private router: Router, private eventService: EventService, private securityService: SecurityService) {}

  ngOnInit() {
    this.eventService.getEvents().then(events => this.events = events);

    let typeStrings;
    this.eventService.getTypes().then(types => typeStrings = types);


    this.types = new Array<Type>();
    //    typeStrings.forEach(type => this.types.push(new Type(type)));
  }

  routerOnDeactivate(next, prev) {
    this.panels.forEach(v => v.cleanUp());
  }

  public onSave(event: Event) {
    this.eventService.saveEvent(event, this.securityService.getUser());
  }

  public onForget(event: Event) {
    this.eventService.forgetEvent(event, this.securityService.getUser());
  }

  isAuthenticated(): boolean {
    return this.securityService.isAuthenticated();
  }

  public isSaved(event: Event): boolean {
    const user = this.securityService.getUser();
    for (let i = 0; i < user.savedEvents.length; i++) {
      if (user.savedEvents[i].id === event.id) {
        return true;
      }
    }
  }

  public getSavedEvents() {
    if (this.securityService.isAuthenticated()) {
      return this.securityService.getUser().savedEvents;
    }
    return null;
  }
}


