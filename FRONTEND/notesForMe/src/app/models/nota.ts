export class Nota {
    titulo:String;
    descripcion:String;
    fechaNota:Date;
    img:String;
    fk_usuario:String;

    constructor(){
        this.titulo = '';
        this.descripcion = '';
        this.fechaNota = new Date();
        this.img = '';
        this.fk_usuario = '';
    }
}