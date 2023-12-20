import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  picker: any;
  sendRegister !: FormGroup;

  ngOnInit() {

  }

  constructor(private fb: FormBuilder) {
    this.validadcionRegister();
  }

  validadcionRegister(): void {
    this.sendRegister = this.fb.group({
      email: new FormControl('',[Validators.required,Validators.email]),
      nombre: new FormControl('',[Validators.required]), 
      apellidos: new FormControl('',[Validators.required]), 
      dni: new FormControl('',[Validators.required,Validators.pattern("^(\d{8})([A-Z])$")]), 
      fechaNacimiento: new FormControl('',[Validators.required]), 
      contrasena: new FormControl('',[Validators.required]), 
      contrasenaAUX: new FormControl('',[Validators.required])
    });
  }

  enviarFormulario(): void {

  }
}
