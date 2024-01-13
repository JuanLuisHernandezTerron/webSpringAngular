export class Nota {
    id:Number;
    titulo:String;
    descripcion:String;
    fechaNota:Date;
    img:String;
    fk_usuario:String;

    constructor(){
        this.id = 0;
        this.titulo = '';
        this.descripcion = '';
        this.fechaNota = new Date();
        this.img = '';
        this.fk_usuario = '';
    }
}