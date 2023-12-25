import {Nota} from "./nota";

export interface usuarioBack {
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    apellidos:string;
    authorities:Array<Object>;
    contrasena:string;
    credentialsNonExpired:boolean;
    dni:string;
    email:string;
    enabled:boolean;
    fechaNacimiento:Date;
    imgPerfil:string;
    nombre:string;
    nota:Array<Nota>;
    password:string;
    role:string;
    username:string;
}