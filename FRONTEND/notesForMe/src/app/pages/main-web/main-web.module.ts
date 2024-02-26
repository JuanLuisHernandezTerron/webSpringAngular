import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainWebRoutingModule } from './main-web-routing.module';
import { MainWebComponent } from './main/main-web.component';
import { MatButtonModule } from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';

@NgModule({
  declarations: [
    MainWebComponent,
  ],
  imports: [
    CommonModule,
    MainWebRoutingModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class MainWebModule { }
