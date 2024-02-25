import { Component, ViewChild, AfterViewInit, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatDialogRef, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent } from '@angular/material/dialog';
import { NuevaNotaComponent } from 'src/app/dialogs/nueva-nota/nueva-nota.component';
import { DialogUpdateNotaComponent } from 'src/app/dialogs/dialog-update-nota/dialog-update-nota.component';
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
  actualizar: Boolean = false;
  value:String = '';

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

  searchAvance(value:any){
    let informacion = {
      "idUsuario":this.usuarioInfo.dni,
      "valor":value.target.value
    }
    this.notaService.busquedaAvanzada(informacion).subscribe(response=>{
      console.log(response);
    },
    error=>{
      console.log(error);
    });
    console.log(value.target.value);
  }

  guardarNota() {
    this.notaEnviar.descripcion = tinymce.activeEditor.getContent();
    let formDataSend: FormData = new FormData();
    console.log(this.notaEnviar);
    if (this.actualizar) {
      this.notaService.actualizarNota(this.notaEnviar).subscribe(response => {
        formDataSend.append("id", this.notaEnviar?.id.toString())
        formDataSend.append("archivo", this.notaEnviar.img_nota as any)
        this.arrayNotas.forEach(x => {
          if (x.id == this.notaEnviar.id) {
            x.descripcion = this.notaEnviar.descripcion;
            x.fechaNota = this.notaEnviar.fechaNota;
            x.titulo = this.notaEnviar.titulo;
          }
        })
        if (this.notaEnviar.img_nota) {
          this.notaService.insertImageNota(formDataSend).subscribe(response => {
            this.arrayNotas.forEach(x => {
              if (x.id == response.nota.id) {
                x.img_nota = response.nota.imgNota;
              }
            })
            this.notaService.ListaNotasObservable.next(this.arrayNotas);
          })
        }
        this.toastr.success("Nota Modificada Correctamente");
        this.actualizar = false;
      },
        err => {
          this.actualizar = false;
        })
    } else {
      this.notaService.insertNotas(this.notaEnviar).subscribe(response => {
        formDataSend.append("id", response?.id.toString())
        formDataSend.append("archivo", this.notaEnviar.img_nota as any)
        if (this.notaEnviar.img_nota) {
          this.notaService.insertImageNota(formDataSend).subscribe(responseAUX => {
            this.arrayNotas.push(responseAUX.nota);
            this.notaService.ListaNotasObservable.next(this.arrayNotas);
            tinymce.activeEditor.setContent("");
            this.toastr.success("Nota Creada Correctamente");
          },
            error => {
              console.log(error);
            })
        }
      }, err => {
        console.error("Error al guardar la nota");
      });
    }
  }

  newNota(enterAnimationDuration: string, exitAnimationDuration: string) {
    const dialogref = this.dialog.open(NuevaNotaComponent, {
      width: '40%',
      enterAnimationDuration,
      exitAnimationDuration,
      /*data : nota,*/
      autoFocus: false
    });

    dialogref.afterClosed().subscribe((x) => {
      this.notaEnviar = x;
      this.actualizar = false;
    })
  }

  updateNota(enterAnimationDuration: string, exitAnimationDuration: string, nota: Nota) {
    const dialogref = this.dialog.open(DialogUpdateNotaComponent, {
      width: '40%',
      enterAnimationDuration,
      exitAnimationDuration,
      data: nota,
      autoFocus: false
    });

    dialogref.afterClosed().subscribe((x) => {
      this.notaEnviar = x;
      this.actualizar = true;
    })
  }

  ajustes(): void {
    this.ordenarFilter = !this.ordenarFilter;
    this.arrayNotas = this.arrayNotas.sort((a: any, b: any) => {
      let fechaNotaA = new Date(a.fechaNota);
      let fechaNotaB = new Date(b.fechaNota);
      return this.ordenarFilter ? fechaNotaA.getTime() - fechaNotaB.getTime() : fechaNotaB.getTime() - fechaNotaA.getTime();
    })
  }
}
