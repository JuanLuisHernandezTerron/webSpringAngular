import { Component,OnInit } from '@angular/core';
import { FormControl,FormBuilder, Validators, FormGroup } from '@angular/forms';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements  OnInit{
  sendLogin !: FormGroup;
  public showPassword: boolean = false;
  hide = true;
  picker: any;
  progressSpinner: boolean = false;

  constructor (private fb:FormBuilder, private authService:AuthServicesService) { 
    this.reactiveFormulario();
  }
  
  ngOnInit() {

  }

  reactiveFormulario(){
    this.sendLogin = this.fb.group({
      email: new FormControl("",[Validators.required, Validators.email]),
      contrasena: new FormControl("",[Validators.required,Validators.minLength(8)])
    })
  }

  public togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  enviarFormulario(){
    let usuarioLogin = {
      email: this.sendLogin.get("email")?.value,
      contrasena:this.sendLogin.get("contrasena")?.value
    }

    this.authService.login(usuarioLogin).subscribe(
      response=>{
        localStorage.setItem("token",response.token);
        this.progressSpinner = false;
              
      },
      err=>{
        console.log(err);
      }
    )    
  }
}
