import {LocationService} from '../location.service';
import {Component, OnInit} from '@angular/core';
import {Location} from '../model/location';
import {SecurityService} from '../security.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-location-info',
  templateUrl: './location-info.component.html',
  styleUrls: ['./location-info.component.css']
})
export class LocationInfoComponent implements OnInit {
  id: string;
  location: Location;
  private sub: any;

  constructor(private route: ActivatedRoute, private router: Router, private locationService: LocationService, private securityService: SecurityService) {}

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.locationService.getLocationById(this.id).then(location => this.location = location);
    });
  }

  isAuthenticated(): boolean {
    return this.securityService.isAuthenticated();
  }

  public isSaved(location: Location): boolean {
    const user = this.securityService.getUser();
    for (let i = 0; i < user.savedLocations.length; i++) {
      if (user.savedLocations[i].id === location.id) {
        return true;
      }
    }
  }

  public onSave(event: Event) {
    this.locationService.saveLocation(this.location, this.securityService.getUser());
  }

  public onForget(event: Event) {
    this.locationService.forgetLocation(this.location, this.securityService.getUser());
  }

}
