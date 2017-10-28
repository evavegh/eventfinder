import {SecurityService} from '../security.service';
import {User} from '../user';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  user: User = new User(null, null, null, null, null, null);

  constructor(private router: Router, private securityService: SecurityService) {}

  ngOnInit() {
  }

  onSubmit() {
    this.securityService.registerUser(this.user);
    this.router.navigate(['/events']);
  }

}
