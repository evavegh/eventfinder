import {EventService} from '../event.service';
import {Component, OnInit, OnDestroy} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';

import {Event} from '../event';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit, OnDestroy {

  lat = 47.5011151657;
  lon = 19.0531965145;
  events: Event[];
  id: string;
  private sub: any;

  infoWindowOpened = null;

  constructor(private route: ActivatedRoute, private router: Router, private eventService: EventService) {}

  clickedMarker(infoWindow) {
    if (this.infoWindowOpened === infoWindow) {
      return;
    }
    if (this.infoWindowOpened !== null) {
      this.infoWindowOpened.close();
    }
    this.infoWindowOpened = infoWindow;
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      console.log(this.id);
      if (this.id === '0') {
        this.eventService.getEvents().then(events => this.events = events);
      } else {
        this.eventService.getEventById(this.id).then(event => this.events = [event]);
      }
    });
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

}
