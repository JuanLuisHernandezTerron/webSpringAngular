import { Component, OnInit } from '@angular/core';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
import { usuarioBack } from 'src/app/models/usuarioBack';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Usuario } from 'src/app/models/usuario';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-profile-user',
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.scss']
})
export class ProfileUserComponent implements OnInit {

  constructor(private authService: AuthServicesService, private fb: FormBuilder, private toastr: ToastrService) {
    this.authService.usuarioInfo$.subscribe(user => {
      this.usuarioInfo = user
      this.validatorUserEdit();
    });
  }

  imageneProducto:File;
  hide: any;
  public showPassword: boolean = false;
  usuarioInfo: usuarioBack = new usuarioBack();
  picker: any;
  usuario: Usuario = new Usuario();
  formEditUsu!: FormGroup;
  progressSpinner: boolean = false;


  ngOnInit(): void {
  }

  imagenesChange(event) {
    // for (let index = 0; index < event.target.files.length; index++) {
    //   this.imageneProducto = event.target.files[index];
    // }
  }

  validatorUserEdit(): void {
    this.formEditUsu = this.fb.group({
      nombre: new FormControl(this.usuarioInfo.nombre, [Validators.required]),
      apellidos: new FormControl(this.usuarioInfo.apellidos, [Validators.required]),
      dni: new FormControl(this.usuarioInfo.dni, [Validators.pattern("^(\\d{8})([A-Z])$"), Validators.required, Validators.maxLength(9), Validators.minLength(9)]),
      fechaNacimiento: new FormControl(this.usuarioInfo.fechaNacimiento, [Validators.required]),
      email: new FormControl(this.usuarioInfo.email, [Validators.email, Validators.required]),
      passwords: this.fb.group({
        contrasena: new FormControl('', [Validators.minLength(8)]),
        repeatContrasena: new FormControl('', [this.matchingPasswords.bind(this)]),
      })
    });
  }

  matchingPasswords(control: FormControl): { [key: string]: boolean } | null {
    const password = this.formEditUsu?.get('passwords.contrasena')?.value;
    const confirmPassword = control.value;
    // Verificar si las contraseñas coinciden
    return password === confirmPassword ? null : { 'passwordMismatch': true };
  }

  previewIMG(event: any): void {
    const input = event.target as HTMLInputElement;
    var formDataSend: FormData = new FormData();
    if (input.files && input.files.length) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        const img = new Image();
        var url = e.target.result;
        img.onload = () => {

          for (let index = 0; index < event.target.files.length; index++) {
            this.imageneProducto = event.target.files[index];
          }

          document.getElementById('img-change')?.setAttribute('src', e.target?.result as string);
          formDataSend.append("id", this.usuarioInfo.dni)
          formDataSend.append("img", this.imageneProducto)
          console.log(this.imageneProducto);
          
          this.authService.changeImage(formDataSend).subscribe(response => {
            console.log(response);
          },
            error => {
              console.log(error);
            })
        };
        img.src = e.target.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  public togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  sendUser(): void {
    this.usuario.dni = this.formEditUsu.get("dni")?.value;
    this.usuario.nombre = this.formEditUsu.get("nombre")?.value;
    this.usuario.apellidos = this.formEditUsu.get("apellidos")?.value;
    this.usuario.email = this.formEditUsu.get("email")?.value;
    this.usuario.fechaNacimiento = this?.formEditUsu?.get("fechaNacimiento")?.value;;

    if (this.formEditUsu.get("passwords.contrasena")?.value == "") {
      this.usuario.contrasena = this.usuarioInfo.contrasena
    } else {
      this.usuario.contrasena = this.formEditUsu.get("passwords.contrasena")?.value;
    }

    this.authService.updateUser(this.usuarioInfo.dni, this.usuario).subscribe(
      response => {
        this.progressSpinner = false;
        this.toastr.success("Usuario Modificado");
        sessionStorage.setItem("token", response.token);
      },
      err => {
        this.progressSpinner = false;
        this.toastr.error("Error al modificar el usuario")
      }
    )
  }
}
