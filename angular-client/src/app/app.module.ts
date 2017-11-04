import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {MenuComponent} from './menu/menu.component';
import {RouterModule, Routes, Router, Route} from '@angular/router';
import {SettingsComponent} from './settings/settings.component';
import {EventComponent} from './event/event.component';
import {TruncatePipe} from './pipe';
import {NamePipe, LocationPipe, CityPipe, StartDatePipe, EndDatePipe, OnlySavedPipe, TypePipe} from './SearchPipe';
import {MapComponent} from './map/map.component';
import {AgmCoreModule} from '@agm/core';
import {DetailsComponent} from './details/details.component';
import {EventService} from './event.service';
import {LoginComponentComponent} from './login-component/login-component.component';
import {SecurityService} from './security.service';
import {DatePickerModule} from 'angular-io-datepicker';
import {RegistrationComponent} from './registration/registration.component';
import {FieldErrorViewComponent} from './field-error-view/field-error-view.component';
import {UserInfoComponent} from './settings/user-info/user-info.component';
import {UserService} from './user.service';
import {LocationInfoComponent} from './location-info/location-info.component';
import {LocationService} from './location.service';

const routes: Route[] = [
  {path: 'home', component: MenuComponent},
  {path: 'settings', component: SettingsComponent},
  {
    path: 'events',
    children: [{
      path: '',
      component: EventComponent
    }
    ]
  },
  {
    path: 'map/:id', component: MapComponent
  },
  {path: 'details/:id', component: DetailsComponent},
  {path: 'login', component: LoginComponentComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'user/:id', component: UserInfoComponent},
  {path: 'location/:id', component: LocationInfoComponent}
];


@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    SettingsComponent,
    EventComponent,
    TruncatePipe,
    NamePipe,
    LocationPipe,
    CityPipe,
    StartDatePipe,
    EndDatePipe,
    OnlySavedPipe,
    TypePipe,
    MapComponent,
    DetailsComponent,
    LoginComponentComponent,
    RegistrationComponent,
    FieldErrorViewComponent,
    UserInfoComponent,
    LocationInfoComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    DatePickerModule,
    RouterModule.forRoot(routes),
    RouterModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBkVggQNc7YWPsjl5tZ5jgQlkkvG_U20vc'
    })
  ],
  exports: [
    ReactiveFormsModule
  ],
  providers: [
    SecurityService,
    EventService,
    UserService,
    LocationService],
  bootstrap: [AppComponent]
})
export class AppModule {}
