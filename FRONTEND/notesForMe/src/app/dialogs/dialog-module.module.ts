import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NuevaNotaComponent } from './nueva-nota/nueva-nota.component';
import {FormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {ReactiveFormsModule} from '@angular/forms';
import {MatNativeDateModule} from '@angular/material/core';
import {MatIconModule} from '@angular/material/icon';
import { DialogUpdateNotaComponent } from './dialog-update-nota/dialog-update-nota.component';
import {MatCheckboxModule} from '@angular/material/checkbox';

@NgModule({
  declarations: [
    NuevaNotaComponent,
    DialogUpdateNotaComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatIconModule,
    MatCheckboxModule
  ]
})
export class DialogModuleModule { }
