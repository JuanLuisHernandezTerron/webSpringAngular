import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent } from '@angular/material/dialog';
import { NuevaNotaComponent } from 'src/app/dialogs/nueva-nota/nueva-nota.component';
import { Nota } from 'src/app/models/nota';
import { usuarioBack } from 'src/app/models/usuarioBack';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
import { NotaServiceService } from 'src/app/services/notaServices/nota-service.service';
declare var tinymce: any;

@Component({
  selector: 'app-main-user-loggin',
  templateUrl: './main-user-loggin.component.html',
  styleUrls: ['./main-user-loggin.component.scss']
})
export class MainUserLogginComponent implements AfterViewInit,OnInit {
  showFiller = true;
  @ViewChild('drawer') openDialog!: any;
  HoraActual: number = new Date().getHours();
  sizeNotes: number = 1;
  countNotas: number = 10;
  usuarioInfo: usuarioBack;
  notaEnviar!:Nota;
  constructor(private dialog: MatDialog,private authService: AuthServicesService,private notaService:NotaServiceService) {

  }
  ngOnInit(): void {
    this.authService.usuarioInfo$.subscribe(x=>{
      this.usuarioInfo = x;
      console.log(x);
      this.comprobarHora()
    })
  }
  ngAfterViewInit(): void {
    tinymce.init({
      selector: '#editor',
      language: 'es',
      branding: false,
      menubar: false,
      toolbar: 'restoredraft | undo redo | styles forecolor | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | image | table tabledelete | tabledrops tablerowprops tablecellprops | tableinsertrowbefore tableinsertrowafter tabledeleterow | tableinsertcolbefore tableinsertcolafter tabledeletecol',
      statusbar: false,
      plugins: 'image | table | autosave',
      setup: function (editor) {
        editor.on('init', function () {
          console.log('TinyMCE initialized');
        });
      }
    });


  }

  bollIcon(): void {
    this.openDialog.toggle();
    this.showFiller = !this.showFiller;
  }
  comprobarHora(): String {
    return (this.HoraActual >= 6 && this.HoraActual <= 12) ?
      `Buenas Días ${this.usuarioInfo.nombre}`
      : (this.HoraActual >= 12 && this.HoraActual <= 17) ?
        `Buenas Tardes ${this.usuarioInfo.nombre}`
        : `Buenas Noches ${this.usuarioInfo.nombre}`;
  }

  guardarNota() {
    this.notaEnviar.descripcion = tinymce.activeEditor.getContent();
    this.notaService.insertNotas(this.notaEnviar).subscribe(x=>console.log(x));
    
    /*
    Así se setea el editor
    tinymce.activeEditor.setContent('<p>Hola a todos</p>');
    */
  }

  newNota(enterAnimationDuration: string, exitAnimationDuration: string) {
    const dialogref = this.dialog.open(NuevaNotaComponent, {
      width: '40%',
      enterAnimationDuration,
      exitAnimationDuration,
      /*data : evento.id,*/
      autoFocus: false
    });

    dialogref.afterClosed().subscribe((x) => {
      this.notaEnviar = x;
      console.log(this.notaEnviar);
    })
  }
}
