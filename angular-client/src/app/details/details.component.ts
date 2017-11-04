import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Event} from '../model/event';
import {RequestOptions} from '@angular/http';
import {EventService} from '../event.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  id: string;
  event: Event;
  private sub: any;

  constructor(private route: ActivatedRoute, private router: Router, private eventService: EventService) {}

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.eventService.getEventById(this.id).then(event => this.event = event);

    });
  }



  cleanUp() {
    console.log('Goodbye');
  }

}
