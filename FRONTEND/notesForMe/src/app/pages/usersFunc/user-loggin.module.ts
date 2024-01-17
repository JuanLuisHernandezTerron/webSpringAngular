import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { userlogginrouting } from './user-loggin-routing.module';
import { ProfileUserComponent } from './profile-user/profile-user.component';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';


@NgModule({
  declarations: [
    ProfileUserComponent
  ],
  imports: [
    CommonModule,
    userlogginrouting,
    MatButtonModule,
    MatCardModule
  ]
})
export class UserLogginModule { }
