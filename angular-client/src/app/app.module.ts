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

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    SettingsComponent,
    EventComponent
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
      {path: 'events', component: EventComponent}
    ]),
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
