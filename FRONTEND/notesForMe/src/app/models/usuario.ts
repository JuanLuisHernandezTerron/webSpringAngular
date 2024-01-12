import { role } from "./role";

export class Usuario {
    dni:string;
    nombre:string;
    apellidos:string;
    email:string;
    contrasena:string;
    role:role;
    enabled:boolean;
    fechaNacimiento:string;

    constructor(){
        this.apellidos = '';
        this.contrasena = '';
        this.dni = '';
        this.email = '';
        this.enabled = false;
        this.fechaNacimiento = '';
        this.nombre = '';
        this.role = role.USER;
    }
}
