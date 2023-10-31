import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from "@angular/common";

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
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
