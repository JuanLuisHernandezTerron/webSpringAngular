import { Component, ViewChild } from '@angular/core';

@Component({
  selector: 'app-main-user-loggin',
  templateUrl: './main-user-loggin.component.html',
  styleUrls: ['./main-user-loggin.component.scss']
})
export class MainUserLogginComponent {
  showFiller = true;
  @ViewChild('drawer') openDialog!: any;
  nombreUsu: String = "Juanlu";
  HoraActual: number = new Date().getHours();
  bollIcon(): void {
    this.openDialog.toggle();
    this.showFiller = !this.showFiller;
  }
  comprobarHora(): String {
    return (this.HoraActual >= 6 && this.HoraActual <= 12) ?
      `Hola Buenas Días ${this.nombreUsu}`
      : (this.HoraActual >= 12 && this.HoraActual <= 17) ?
        `Hola Buenas Tardes ${this.nombreUsu}`
        : `Hola Buenas Noches ${this.nombreUsu}`;
  }

}
