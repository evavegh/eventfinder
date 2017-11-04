import {SecurityService} from '../security.service';
import {User} from '../model/user';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormGroup, FormControl, Validators, ReactiveFormsModule, FormsModule, FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  user: User = new User(null, null, null, null, null, null, null, null, null, null, null);
  user_form: FormGroup;

  constructor(private router: Router, private securityService: SecurityService, private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.user_form = this.formBuilder.group({
      email: new FormControl('', [
        Validators.required,
        Validators.email
      ]),
      id: new FormControl(),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(31),
      ]),
      name: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(31),
      ])
    });
  }

  onSubmit() {
    if (this.user_form.valid) {
      console.log(this.user);
      console.log('success');
      this.securityService.registerUser(this.user);
      this.router.navigate(['/events']);
    } else {
      console.log('failure');
    }
  }

  isFieldValid(field: string) {
    return !this.user_form.get(field).valid && this.user_form.get(field).touched;
  }

  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }

}
