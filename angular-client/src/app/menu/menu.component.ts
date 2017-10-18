import {SecurityService} from '../security.service';
import {Component, OnInit} from '@angular/core';
import {RouterModule, Routes, Router} from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})

export class MenuComponent implements OnInit {



  constructor(private router: Router, private securityService: SecurityService) {}

  ngOnInit() {
  }

  isAuthenticated(): boolean {
    return this.securityService.isAuthenticated();
  }

}


