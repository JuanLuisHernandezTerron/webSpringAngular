import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Usuario } from 'src/app/models/usuario';
import { role } from 'src/app/models/role';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  picker: any;
  sendRegister !: FormGroup;
  hide = true;
  usuario: Usuario = {} as Usuario;
  progressSpinner: boolean = false;

  public showPassword: boolean = false;
  ngOnInit() {

  }

  constructor(private fb: FormBuilder, private authService:AuthServicesService,private datepipe:DatePipe, private route:Router) {
    this.validadcionRegister();
  }

  public togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  validadcionRegister(): void {
    this.sendRegister = this.fb.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      nombre: new FormControl('', [Validators.required]),
      apellidos: new FormControl('', [Validators.required]),
      dni: new FormControl('', [Validators.pattern("^(\\d{8})([A-Z])$"), Validators.required, Validators.maxLength(9), Validators.minLength(9)]),
      fechaNacimiento: new FormControl('', [Validators.required]),
      contrasena: new FormControl('', [Validators.required, Validators.minLength(8)]),
      contrasenaAUX: new FormControl('', [Validators.required,this.matchingPasswords.bind(this)])
    });
  }

  matchingPasswords(control: FormControl): { [key: string]: boolean } | null {
    const password = this.sendRegister?.get('contrasena')?.value;
    const confirmPassword = control.value;

    // Verificar si las contraseñas coinciden
    return password === confirmPassword ? null : { 'passwordMismatch': true };
  }

  enviarFormulario(): void {
    this.progressSpinner = true;
    let fechaComoString:Date = this?.sendRegister?.get("fechaNacimiento")?.value;
    this.usuario.dni = this?.sendRegister?.get("dni")?.value;
    this.usuario.nombre = this?.sendRegister?.get("nombre")?.value;
    this.usuario.apellidos = this?.sendRegister?.get("apellidos")?.value;
    this.usuario.email = this?.sendRegister?.get("email")?.value;
    this.usuario.contrasena = this?.sendRegister?.get("contrasena")?.value;
    this.usuario.role = role.USER;
    this.usuario.enabled = true;
    this.usuario.fechaNacimiento = fechaComoString.toUTCString();
    this.authService.register(this.usuario).subscribe(
    response=>{
      sessionStorage.setItem("token",response.token);
      this.progressSpinner = false;
      this.route.navigate(['/mainUserLoggin']);
      this.authService.setLogged();
      this.authService.UsuarioInfo.next(response.usuario);
    },
    err=>{
      console.log(err);
      this.progressSpinner = false;
    });
  }
}
