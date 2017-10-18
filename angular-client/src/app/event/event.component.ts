import {DetailsComponent} from '../details/details.component';
import {EventService} from '../event.service';
import {Component, OnInit, ViewChildren} from '@angular/core';
import {Router} from '@angular/router';
import {NamePipe, LocationPipe, CityPipe, StartDatePipe, EndDatePipe} from '../SearchPipe';

import {Event} from '../event';
import {Type} from '../type';

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

  constructor(private router: Router, private eventService: EventService) {}

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

}
