import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from 'src/app/models/usuario';
import { Observable } from 'rxjs';
import {responsePost} from "./../../models/responsePost";
@Injectable({
  providedIn: 'root'
})
export class AuthServicesService {
  constructor(private http: HttpClient) { }
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  
  register(usuario:Usuario):Observable<responsePost>{    
    return this.http.post<responsePost>('http://localhost:8080/api/auth/cliente/registerUser',usuario,{headers:this.httpHeaders});
  }

  login(userLogin:any):Observable<responsePost>{
    return this.http.post<responsePost>('http://localhost:8080/api/auth/cliente/loginUser',userLogin,{headers:this.httpHeaders});
  }
}
