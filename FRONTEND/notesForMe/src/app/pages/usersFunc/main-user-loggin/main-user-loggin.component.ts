import { Component, ViewChild, AfterViewInit } from '@angular/core';
import {MatDialog,MatDialogRef,MatDialogActions,MatDialogClose,MatDialogTitle,MatDialogContent} from '@angular/material/dialog';
import { NuevaNotaComponent } from 'src/app/dialogs/nueva-nota/nueva-nota.component';
declare var tinymce: any;

@Component({
  selector: 'app-main-user-loggin',
  templateUrl: './main-user-loggin.component.html',
  styleUrls: ['./main-user-loggin.component.scss']
})
export class MainUserLogginComponent implements AfterViewInit {
  showFiller = true;
  @ViewChild('drawer') openDialog!: any;
  nombreUsu: String = "Juanlu";
  HoraActual: number = new Date().getHours();
  sizeNotes: number = 1;
  countNotas: number = 10;

  constructor (private dialog:MatDialog) { }

  ngAfterViewInit(): void {
    tinymce.init({
      selector: '#editor',
      language: 'es',
      branding: false,
      menubar: false,
      toolbar: 'restoredraft | undo redo | styles forecolor | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | image | table tabledelete | tabledrops tablerowprops tablecellprops | tableinsertrowbefore tableinsertrowafter tabledeleterow | tableinsertcolbefore tableinsertcolafter tabledeletecol',
      statusbar: false,
      plugins: 'image | table | autosave'
    });
  }

  bollIcon(): void {
    this.openDialog.toggle();
    this.showFiller = !this.showFiller;
  }
  comprobarHora(): String {
    return (this.HoraActual >= 6 && this.HoraActual <= 12) ?
      `Buenas Días ${this.nombreUsu}`
      : (this.HoraActual >= 12 && this.HoraActual <= 17) ?
        `Buenas Tardes ${this.nombreUsu}`
        : `Buenas Noches ${this.nombreUsu}`;
  }

  guardarNota() {
    console.log(tinymce.activeEditor.getContent());
    /*
    Así se setea el editor
    tinymce.activeEditor.setContent('<p>Hola a todos</p>');
    */
  }

  newNota(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.dialog.open(NuevaNotaComponent, {
      width: '40%',
      enterAnimationDuration,
      exitAnimationDuration,
      /*data : evento.id,*/
      autoFocus: false
    }); 
  }
}
