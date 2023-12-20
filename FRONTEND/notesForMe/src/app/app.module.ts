import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HeaderNotesForMeComponent } from './common/components/header-notes-for-me/header-notes-for-me.component';
import { FooterNotesForMeComponent } from './common/components/footer-notes-for-me/footer-notes-for-me.component';
@NgModule({
  declarations: [
    AppComponent,
    FooterNotesForMeComponent,
    HeaderNotesForMeComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    RouterModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
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
