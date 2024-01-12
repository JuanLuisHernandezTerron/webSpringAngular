import {Nota} from "./nota";
import { role } from "./role";

export class usuarioBack {
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    apellidos:string;
    authorities:Array<Object>;
    contrasena:string;
    credentialsNonExpired:boolean;
    dni:string;
    email:string;
    enabled:boolean;
    fechaNacimiento:string;
    imgPerfil:string;
    nombre:string;
    nota:Array<Nota>;
    password:string;
    role:role;
    username:string;

    constructor () {
        this.accountNonExpired = false;
        this.accountNonLocked = false;
        this.apellidos = '';
        this.authorities = [];
        this.contrasena = '';
        this.credentialsNonExpired = false;
        this.dni = '';
        this.email = '';
        this.enabled = false;
        this.fechaNacimiento = '';
        this.imgPerfil = '';
        this.nombre = '';
        this.nota = [];
        this.password = '';
        this.role = role.USER;
        this.username = '';
    }
}