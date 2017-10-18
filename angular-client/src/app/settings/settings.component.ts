import {SecurityService} from '../security.service';
import {User} from '../user';
import {Component, OnInit} from '@angular/core';
import {RouterModule, Routes, Router} from '@angular/router';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  user: User;

  constructor(private router: Router, private securityService: SecurityService) {}

  ngOnInit() {
    this.user = this.securityService.getUser();
  }

  onSubmit() {
    console.log('submitting...');
    this.securityService.postUser(this.user);
  }

}
