import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from 'src/app/models/usuario';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthServicesService {
  constructor(private http: HttpClient) { }
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  
  register(usuario:Usuario):Observable<Usuario>{    
    return this.http.post<Usuario>('http://localhost:8080/api/auth/cliente/registerUser',usuario,{headers:this.httpHeaders});
  }
}
