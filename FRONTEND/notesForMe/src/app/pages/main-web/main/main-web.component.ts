import { Component, OnInit } from '@angular/core';
import { NotaServiceService } from 'src/app/services/notaServices/nota-service.service';
import AOS from 'aos';
import * as $ from 'jquery';

@Component({
  selector: 'app-main-web',
  templateUrl: './main-web.component.html',
  styleUrls: ['./main-web.component.scss']
})
export class MainWebComponent implements OnInit {
  constructor(private notaService:NotaServiceService) { }
  notas:number;
  ngOnInit(): void {
    AOS.init();
  }
}
