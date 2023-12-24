import { role } from "./role";

export interface Usuario {
    dni:string,
    nombre:string,
    apellidos:string,
    email:string,
    contrasena:string,
    role:role,
    enabled:boolean,
    fechaNacimiento:string,
}
