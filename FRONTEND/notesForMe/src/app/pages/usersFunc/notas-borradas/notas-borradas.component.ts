import { Component, ViewChild } from '@angular/core';
import { Nota } from 'src/app/models/nota';
import { NotaServiceService } from 'src/app/services/notaServices/nota-service.service';
import { ToastrService } from 'ngx-toastr';
import { MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-notas-borradas',
  templateUrl: './notas-borradas.component.html',
  styleUrls: ['./notas-borradas.component.scss']
})
export class NotasBorradasComponent{

  constructor(private notaService: NotaServiceService, private toastr: ToastrService,private dialog:MatDialog) {
    this.notaService.ListaNotas$.subscribe(x => {
      this.arrayNotas = x;
      this.devolverNotasBorrada();
    })
  }

  showFiller: Boolean = true;
  @ViewChild('drawer') openDialog!: any;
  arrayNotas!: Array<Nota>;

  bollIcon(): void {
    this.openDialog.toggle();
    this.showFiller = !this.showFiller;
  }

  

  restoreNota(nota: Nota): void {
    nota.borrada = false;
    this.notaService.NotaBorrada(nota).subscribe(
      response => {
        this.arrayNotas = this.arrayNotas.filter(notaAUX => (notaAUX.id == nota.id) ? notaAUX = nota : notaAUX);
        this.notaService.ListaNotasObservable.next(this.arrayNotas);
        this.toastr.success("Nota restaurada");
      },
      error => {
        this.toastr.error("Nota no restaurada");
      }
    );
  }

  devolverNotasBorrada():Array<Nota>{
    return this.arrayNotas.filter(nota=>nota.borrada);
  }

  deleteNota(nota: Nota): void {
    this.arrayNotas = this.arrayNotas.filter(notaAux => notaAux.id !== nota.id);
    this.notaService.ListaNotasObservable.next(this.arrayNotas);
    this.notaService.deleteNota(nota.id).subscribe(response => {
      this.toastr.success("Nota eliminada");
    }, error => {
      this.toastr.error("No se ha podido eliminar Correctamente")    
    });
  }
}
