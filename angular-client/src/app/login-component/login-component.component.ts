import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-component',
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.css']
})
export class LoginComponentComponent implements OnInit {

  model: any = {};
  loading = false;
  error = '';

  constructor(
    private router: Router) {}

  ngOnInit() {
    // reset login status
  }

}
