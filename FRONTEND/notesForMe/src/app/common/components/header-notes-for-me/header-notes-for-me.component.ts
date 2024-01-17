import { Component,OnInit } from '@angular/core';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-header-notes-for-me',
  templateUrl: './header-notes-for-me.component.html',
  styleUrls: ['./header-notes-for-me.component.scss']
})
export class HeaderNotesForMeComponent implements OnInit{
  public isLogged:Boolean;
  constructor(private route:Router,private authService:AuthServicesService) {
  }
  
  ngOnInit(): void {
    this.authService.isLogged$.subscribe(x=> this.isLogged = x);
  }

  logout():void{
    this.authService.logout();
    this.route.navigate([""])
  }

  routeHeader(){
    (sessionStorage.getItem('logged')) ? this.route.navigate(["/mainUserLoggin"]) : this.route.navigate(["/"]);
  }
}
