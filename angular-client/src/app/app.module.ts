import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {MenuComponent} from './menu/menu.component';
import {RouterModule, Routes, Router} from '@angular/router';
import {SettingsComponent} from './settings/settings.component';
import {EventComponent} from './event/event.component';
import {TruncatePipe} from './pipe';
import {NamePipe, LocationPipe, CityPipe, StartDatePipe, EndDatePipe, OnlySavedPipe} from './SearchPipe';
import {MapComponent} from './map/map.component';
import {AgmCoreModule} from '@agm/core';
import {DetailsComponent} from './details/details.component';
import { EventService } from './event.service';
import { LoginComponentComponent } from './login-component/login-component.component';
import { SecurityService } from './security.service';
import {DatePickerModule} from 'angular-io-datepicker';
import { RegistrationComponent } from './registration/registration.component';

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
    MapComponent,
    DetailsComponent,
    LoginComponentComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    DatePickerModule,
    RouterModule.forRoot([
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
      {path: 'registration', component: RegistrationComponent}
    ]),
    RouterModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBkVggQNc7YWPsjl5tZ5jgQlkkvG_U20vc'
    })
  ],
  providers: [
  SecurityService,
  EventService],
  bootstrap: [AppComponent]
})
export class AppModule {}
