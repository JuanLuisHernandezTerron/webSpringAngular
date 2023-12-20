import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MainWebRoutingModule } from './main-web-routing.module';
import { MainWebComponent } from './main/main-web.component';

import {MatButtonModule} from '@angular/material/button';

@NgModule({
  declarations: [
    MainWebComponent,
  ],
  imports: [
    CommonModule,
    MainWebRoutingModule,
    MatButtonModule
  ]
})
export class MainWebModule { }
