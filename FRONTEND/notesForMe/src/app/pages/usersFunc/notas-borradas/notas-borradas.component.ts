import { Component,ViewChild } from '@angular/core';

@Component({
  selector: 'app-notas-borradas',
  templateUrl: './notas-borradas.component.html',
  styleUrls: ['./notas-borradas.component.scss']
})
export class NotasBorradasComponent {
  showFiller:Boolean = true;
  @ViewChild('drawer') openDialog!: any;

  bollIcon(): void {
    this.openDialog.toggle();
    this.showFiller = !this.showFiller;
  }
}
