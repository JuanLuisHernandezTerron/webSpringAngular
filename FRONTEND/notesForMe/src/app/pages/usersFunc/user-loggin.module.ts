import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatIconModule} from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { userlogginrouting } from './user-loggin-routing.module';
import { ProfileUserComponent } from './profile-user/profile-user.component';


@NgModule({
  declarations: [
    ProfileUserComponent
  ],
  imports: [
    CommonModule,
    userlogginrouting,
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatInputModule,
    MatDatepickerModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class UserLogginModule { }
