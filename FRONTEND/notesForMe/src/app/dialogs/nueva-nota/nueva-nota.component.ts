import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Nota } from 'src/app/models/nota';
import { AuthServicesService } from 'src/app/services/authServices/auth-services.service';
@Component({
  selector: 'app-nueva-nota',
  templateUrl: './nueva-nota.component.html',
  styleUrls: ['./nueva-nota.component.scss']
})
export class NuevaNotaComponent {
  value = '';
  formNota !: FormGroup;
  srcResult: any;
  imageneProducto: string;
  fileIMG:any;

  constructor(private fb: FormBuilder, public dialogRef: MatDialogRef<NuevaNotaComponent>,private authService:AuthServicesService) {
    this.validationFormulario();    
  }

  validationFormulario(): void {
    this.formNota = this.fb.group({
      titulo: new FormControl("", [Validators.required]),
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
        id:null,
        titulo: this.formNota.get('titulo').value,
        img_nota: this.fileIMG,
        descripcion: undefined,
        fechaNota: new Date(),
        borrada:false,
        fk_usuario: x.dni
      };
      this.dialogRef.close(nota);
    })
  }
}

