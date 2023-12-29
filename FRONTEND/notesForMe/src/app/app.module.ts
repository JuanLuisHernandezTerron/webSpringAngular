import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from "@angular/common";
import { RouterModule} from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';


import { AppComponent } from './app.component';
import { HeaderNotesForMeComponent } from './common/components/header-notes-for-me/header-notes-for-me.component';
import { FooterNotesForMeComponent } from './common/components/footer-notes-for-me/footer-notes-for-me.component';
import { MainUserLogginComponent } from './pages/usersFunc/main-user-loggin/main-user-loggin.component';
@NgModule({
  declarations: [
    AppComponent,
    FooterNotesForMeComponent,
    HeaderNotesForMeComponent,
    MainUserLogginComponent,
  ],
  imports: [
    BrowserModule,
    CommonModule,
    RouterModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    MatNativeDateModule,
    MatRippleModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatTooltipModule,
    MatButtonModule,
    MatSidenavModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor () {
    console.log("appmodule loaded")
  }
}
