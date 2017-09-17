import {EventService} from '../event.service';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {Event} from '../event';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  lat = 47.5011151657;
  lon = 19.0531965145;
  events: Event[];

  infoWindowOpened = null;

  constructor(private router: Router, private eventService: EventService) {}

  ngOnInit() {
    this.eventService.getEvents().then(events => this.events = events);
  }

  clickedMarker(infoWindow) {
    console.log('yey');
    if (this.infoWindowOpened === infoWindow) {
      return;
    }
    if (this.infoWindowOpened !== null) {
      this.infoWindowOpened.close();
    }
    this.infoWindowOpened = infoWindow;
  }

}
