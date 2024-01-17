import { Component,OnInit } from '@angular/core';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
import { usuarioBack } from 'src/app/models/usuarioBack';
@Component({
  selector: 'app-profile-user',
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.scss']
})
export class ProfileUserComponent implements OnInit{

  constructor (private authService:AuthServicesService) {}
  imageneProducto: string;
  usuarioInfo:usuarioBack = new usuarioBack();
  ngOnInit(): void {
    this.authService.usuarioInfo$.subscribe(user=>this.usuarioInfo = user);
  }

  imagenesChange(event) {
    for (let index = 0; index < event.target.files.length; index++) {
      this.imageneProducto = event.target.files[index];
    }
  }

  
  previewIMG(event: any): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        const img = new Image();
        var url = e.target.result;
        img.onload = () => {
          document.getElementById('img-bebida')?.setAttribute('src', e.target?.result as string);
        };
        img.src = e.target.result as string;
      };
      reader.readAsDataURL(file);
    }
  }
}
