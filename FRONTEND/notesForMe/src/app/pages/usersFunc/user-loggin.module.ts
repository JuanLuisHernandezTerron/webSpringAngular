import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatIconModule} from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NavSideComponent } from 'src/app/common/components/nav-side/nav-side.component';
import { userlogginrouting } from './user-loggin-routing.module';
import { ProfileUserComponent } from './profile-user/profile-user.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import { MainUserLogginComponent } from './main-user-loggin/main-user-loggin.component';
import {MatChipsModule} from '@angular/material/chips';
import {MatTooltipModule} from '@angular/material/tooltip';
import { NotasBorradasComponent } from './notas-borradas/notas-borradas.component';

@NgModule({
  declarations: [
    ProfileUserComponent,
    MainUserLogginComponent,
    NavSideComponent,
    NotasBorradasComponent
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
    ReactiveFormsModule,
    MatSidenavModule,
    MatChipsModule,
    MatTooltipModule
  ]
})
export class UserLogginModule { }
