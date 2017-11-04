import {User} from '../../model/user';
import { SecurityService } from '../../security.service';
import {UserService} from '../../user.service';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {
  id: string;
  user: User;
  private sub: any;

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private securityService: SecurityService) {}

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.userService.getUserById(this.id).then(user => this.user = user);
    });

  }

  isAuthenticated(): boolean {
    return this.securityService.isAuthenticated();
  }

  public isSaved(advertiser: User): boolean {
    const user = this.securityService.getUser();
    for (let i = 0; i < user.savedAdvertisers.length; i++) {
      if (user.savedAdvertisers[i].id === advertiser.id) {
        return true;
      }
    }
  }

  public onSave(event: Event) {
    this.userService.saveAdvertiser(this.user, this.securityService.getUser());
  }

  public onForget(event: Event) {
    this.userService.forgetAdvertiser(this.user, this.securityService.getUser());
  }

}
