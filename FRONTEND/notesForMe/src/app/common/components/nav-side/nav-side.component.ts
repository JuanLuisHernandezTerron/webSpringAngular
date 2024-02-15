import { Component,OnInit } from '@angular/core';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
import { usuarioBack } from 'src/app/models/usuarioBack';
@Component({
  selector: 'app-nav-side',
  templateUrl: './nav-side.component.html',
  styleUrls: ['./nav-side.component.scss']
})
export class NavSideComponent implements OnInit  {
  constructor (private authService:AuthServicesService ){ }
  usuarioInfo:usuarioBack = new usuarioBack();
  
  ngOnInit(): void {
    this.authService.usuarioInfo$.subscribe(user=> this.usuarioInfo = user);
  }

  
}
