import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MdButtonModule, MdCardModule, MdMenuModule, MdToolbarModule, MdIconModule} from '@angular/material';
import {MdSidenavModule} from '@angular/material';

import {AppComponent} from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MenuComponent } from './menu/menu.component';
import { CollapseModule } from 'ngx-bootstrap';
import {RouterModule, Routes, Router} from '@angular/router';
import { SettingsComponent } from './settings/settings.component';
import { EventComponent } from './event/event.component';
import { TruncatePipe } from './pipe';
import { MapComponent } from './event/map/map.component';
import { AgmCoreModule } from '@agm/core';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    SettingsComponent,
    EventComponent,
    TruncatePipe,
    MapComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    BrowserAnimationsModule,
    MdButtonModule,
    MdMenuModule,
    MdCardModule,
    MdToolbarModule,
    MdIconModule,
    MdSidenavModule,
    CollapseModule,
    RouterModule.forRoot([
      {path: 'home', component: MenuComponent},
      {path: 'settings', component: SettingsComponent},
      {path: 'events', component: EventComponent},
      {path: 'map', component: MapComponent}
    ]),
    RouterModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBkVggQNc7YWPsjl5tZ5jgQlkkvG_U20vc'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
