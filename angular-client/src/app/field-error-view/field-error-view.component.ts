import {Component, OnInit, Input} from '@angular/core';

@Component({
  selector: 'app-field-error-view',
  templateUrl: './field-error-view.component.html',
  styleUrls: ['./field-error-view.component.css']
})
export class FieldErrorViewComponent implements OnInit {

  @Input() errorMsg: string;
  @Input() displayError: boolean;

  constructor() {}

  ngOnInit() {
  }

}
