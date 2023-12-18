import { role } from "./role";

export interface Usuario {
    dni:string,
    nombre:string,
    apellidos:string,
    email:string,
    contrasena:string,
    fecha_nacimiento:Date,
    role:role,
    enabled:boolean,
}
