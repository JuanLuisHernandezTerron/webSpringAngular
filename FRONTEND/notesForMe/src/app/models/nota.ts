export class Nota {
    id:Number;
    titulo:String;
    descripcion:String;
    fechaNota:Date;
    borrada:Boolean;
    img_nota:String;
    fk_usuario:String;

    constructor(){
        this.id = 0;
        this.titulo = '';
        this.descripcion = '';
        this.fechaNota = new Date();
        this.img_nota = '';
        this.borrada = false;
        this.fk_usuario = '';
    }
}