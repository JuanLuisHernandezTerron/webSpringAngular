import { Component,ViewChild } from '@angular/core';
import { Nota } from 'src/app/models/nota';
import { NotaServiceService } from 'src/app/services/notaServices/nota-service.service';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-notas-borradas',
  templateUrl: './notas-borradas.component.html',
  styleUrls: ['./notas-borradas.component.scss']
})
export class NotasBorradasComponent {

  constructor(private notaService:NotaServiceService, private toastr:ToastrService) {
    this.notaService.ListaNotas$.subscribe(x=>{
        this.arrayNotas = x.filter(nota=>nota.borrada === true);
    })
  }
  showFiller:Boolean = true;
  @ViewChild('drawer') openDialog!: any;
  arrayNotas!:Array<Nota>;

  bollIcon(): void {
    this.openDialog.toggle();
    this.showFiller = !this.showFiller;
  }

  restoreNota(nota:Nota):void{    
    nota.borrada = false;
    this.notaService.NotaBorrada(nota).subscribe(
      response=>{
        this.arrayNotas = this.arrayNotas.filter(notaAUX=>(notaAUX.id == nota.id) ? notaAUX = nota : notaAUX);
        this.notaService.ListaNotasObservable.next(this.arrayNotas);
      },
      error=>{
        console.log(error);
      }
    );
  }

  deleteNota(nota:Nota):void{
    this.arrayNotas = this.arrayNotas.filter(nota => nota.id !== nota.id);
    this.notaService.ListaNotasObservable.next(this.arrayNotas);
    this.notaService.deleteNota(nota.id).subscribe(response=>{
      (response.mensaje.includes("eliminiada")) ? this.toastr.success("Nota Eliminada Permanentemente") : this.toastr.error("No se ha podido eliminar Correctamente");
    },error=>{

    });
  }
}
