import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
import { Nota } from 'src/app/models/nota';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-dialog-update-nota',
  templateUrl: './dialog-update-nota.component.html',
  styleUrls: ['./dialog-update-nota.component.scss']
})
export class DialogUpdateNotaComponent {
  formNota!:FormGroup;
  imageneProducto: string;
  fileIMG:any;
  value:any = '';
  notaAUX:any;
  constructor (private fb: FormBuilder, private authService:AuthServicesService,public dialogRef: MatDialogRef<DialogUpdateNotaComponent>,@Inject(MAT_DIALOG_DATA) public data) { 
    this.notaAUX = data;
    console.log(this.notaAUX);
    
    this.validationFormulario();
  }

  validationFormulario(): void {
    this.formNota = this.fb.group({
      titulo: new FormControl(this.notaAUX.titulo, [Validators.required]),
      img: new FormControl("", [])
    })
  }

  
  imagenesChange(event) {
    for (let index = 0; index < event.target.files.length; index++) {
      this.imageneProducto = event.target.files[index];
    }
  }

  previewIMG(event: any): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        const img = new Image();
        var url = e.target.result;
        img.onload = () => {
          document.getElementById('img-bebida')?.setAttribute('src', e.target?.result as string);
          this.fileIMG = event.target.files[0];
        };
        img.src = e.target.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  enviarNota(): void {
    this.authService.usuarioInfo$.subscribe(x=>{
      let nota: Nota = {
        id:this.notaAUX.id,
        titulo: this.formNota.get('titulo').value,
        img_nota: this.fileIMG,
        descripcion: this.notaAUX.descripcion,
        fechaNota: this.notaAUX.fechaNota,
        borrada:this.notaAUX.borrada,
        fk_usuario: x.dni
      };
      this.dialogRef.close(nota);
    })
  }
}

