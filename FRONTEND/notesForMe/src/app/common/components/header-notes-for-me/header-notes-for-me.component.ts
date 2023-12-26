import { Component,OnInit } from '@angular/core';

@Component({
  selector: 'app-header-notes-for-me',
  templateUrl: './header-notes-for-me.component.html',
  styleUrls: ['./header-notes-for-me.component.scss']
})
export class HeaderNotesForMeComponent implements OnInit{

  public isLogged:Boolean = false;
  
  constructor() { }

  ngOnInit(): void {
    
  }


}
