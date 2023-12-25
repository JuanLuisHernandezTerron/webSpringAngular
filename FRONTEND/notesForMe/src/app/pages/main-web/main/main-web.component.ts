import { Component, OnInit } from '@angular/core';
import { ServiceUserService } from 'src/app/services/service-user.service';
@Component({
  selector: 'app-main-web',
  templateUrl: './main-web.component.html',
  styleUrls: ['./main-web.component.scss']
})
export class MainWebComponent implements OnInit{
  constructor (private serviceUser:ServiceUserService) {}
  ngOnInit(): void {
    /*this.serviceUser.getUsuarios().subscribe(data=>console.log(data));*/
  }
}
