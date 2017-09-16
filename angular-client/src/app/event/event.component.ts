import { EventService } from '../event.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Event } from '../event';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  events: Event[];

  constructor(private router: Router, private eventService: EventService) { }

  ngOnInit() {
    this.eventService.getEvents().then(events => this.events = events);
  }

}
