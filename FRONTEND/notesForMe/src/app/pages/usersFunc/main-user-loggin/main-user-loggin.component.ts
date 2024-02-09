import { Component, ViewChild, AfterViewInit, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatDialogRef, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent } from '@angular/material/dialog';
import { NuevaNotaComponent } from 'src/app/dialogs/nueva-nota/nueva-nota.component';
import { Nota } from 'src/app/models/nota';
import { ToastrService } from 'ngx-toastr';
import { Usuario } from 'src/app/models/usuario';
import { usuarioBack } from 'src/app/models/usuarioBack';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
import { NotaServiceService } from 'src/app/services/notaServices/nota-service.service';
declare var tinymce: any;

@Component({
  selector: 'app-main-user-loggin',
  templateUrl: './main-user-loggin.component.html',
  styleUrls: ['./main-user-loggin.component.scss']
})
export class MainUserLogginComponent implements OnInit, OnDestroy, AfterViewInit {
  showFiller = true;
  @ViewChild('drawer') openDialog!: any;
  HoraActual: number = new Date().getHours();
  sizeNotes: number = 1;
  countNotas: number = 10;
  usuarioInfo: usuarioBack;
  usuario: Usuario = new Usuario();
  notaEnviar!: Nota;
  arrayNotas!: Array<Nota>;
  ordenarFilter: Boolean = true;

  constructor(private toastr: ToastrService, private dialog: MatDialog, private authService: AuthServicesService, private notaService: NotaServiceService) {

  }

  ngAfterViewInit(): void {
    this.inicializarTinymce();
  }

  ngOnDestroy(): void {
    tinymce.remove('#editor')
  }

  ngOnInit(): void {
    this.authService.usuarioInfo$.subscribe(x => {
      this.usuarioInfo = x;
      this.comprobarHora();
      this.notaService.infoNotas(x.dni);
      this.notaService.ListaNotas$.subscribe(x => this.arrayNotas = x);
    })
  }

  inicializarTinymce(): void {
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

  viewNota(idNota: Number): void {
    this.arrayNotas.filter(nota => {
      (nota.id === idNota) ? tinymce.activeEditor.setContent(nota.descripcion) : '';
    })
  }

  filterArray(): Array<Nota> {
    return this.arrayNotas.filter(x => x.borrada === false);
  }

  deleteNote(nota: Nota): void {
    this.arrayNotas.forEach(notaAUX => {
      if (notaAUX.id == nota.id) {
        notaAUX.borrada = true;
      }
    })
    this.notaService.NotaBorrada(nota).subscribe(response => {
      this.notaService.ListaNotasObservable.next(this.arrayNotas);
      this.toastr.success("Nota borrada Temporalmente")
    },
      error => {
        this.toastr.error("No se ha podido borrar la nota")
      })
  }

  guardarNota() {
    this.notaEnviar.descripcion = tinymce.activeEditor.getContent();
    this.notaService.insertNotas(this.notaEnviar).subscribe(response => {
      this.arrayNotas.push(response);
      this.notaService.ListaNotasObservable.next(this.arrayNotas);
      tinymce.activeEditor.setContent("")
      this.toastr.success("Nota Creada Correctamente");
    }, err => {
      console.error("Error al guardar la nota");
    });
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
    })
  }

  ajustes(tipoAjuste: String): void {
    if (tipoAjuste === "ordenar") {
      this.ordenarFilter = !this.ordenarFilter;
      this.arrayNotas = this.arrayNotas.sort((a: any, b: any) => {
        let fechaNotaA = new Date(a.fechaNota);
        let fechaNotaB = new Date(b.fechaNota);
        return this.ordenarFilter ? fechaNotaA.getTime() - fechaNotaB.getTime() : fechaNotaB.getTime() - fechaNotaA.getTime();
      });
    }
  }
}
