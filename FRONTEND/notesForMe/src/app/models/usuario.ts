import { role } from "./role";

export interface Usuario {
    dni:string,
    nombre:string,
    apellidos:string,
    email:string,
    contrasena:string,
    FechaNacimiento:string,
    role:role,
    enabled:boolean,
}
