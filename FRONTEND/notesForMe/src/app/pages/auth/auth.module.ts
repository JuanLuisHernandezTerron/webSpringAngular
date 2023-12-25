import { NgModule } from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCardModule} from '@angular/material/card';
import {ReactiveFormsModule} from '@angular/forms';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {FormsModule} from '@angular/forms';
import {MatNativeDateModule} from '@angular/material/core';
import { AuthRoutingModule } from './auth-routing.module';
import { RegisterComponent } from './register/register.component';
import {MatIconModule} from '@angular/material/icon';
import { DatePipe } from '@angular/common';
import { LoginComponent } from './login/login.component';


@NgModule({
  declarations: [
    RegisterComponent,
    LoginComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    MatFormFieldModule,
    MatCardModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatInputModule,
    MatNativeDateModule,
    MatButtonModule,
    FormsModule,
    MatIconModule
  ],
  providers:[
    DatePipe
  ]
})
export class AuthModule { }
