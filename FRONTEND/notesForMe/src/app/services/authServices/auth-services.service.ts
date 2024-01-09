import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from 'src/app/models/usuario';
import { usuarioBack } from 'src/app/models/usuarioBack';
import { BehaviorSubject, Observable, Subject, map } from 'rxjs';
import {responsePost} from "./../../models/responsePost";
@Injectable({
  providedIn: 'root'
})
export class AuthServicesService implements OnInit{
  public isloggedObservable = new BehaviorSubject<boolean>(JSON.parse(sessionStorage.getItem('logged')));
  isLogged$= this.isloggedObservable.asObservable();
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public UsuarioInfo = new BehaviorSubject<usuarioBack>(null);
  public usuarioInfo$ = this.UsuarioInfo.asObservable();
  constructor(private http: HttpClient) { 
  }
  ngOnInit(): void {
    
  }
  
  register(usuario:Usuario):Observable<responsePost>{
    return this.http.post<responsePost>('http://localhost:8080/api/auth/cliente/registerUser',usuario,{headers:this.httpHeaders});
  }

  setLogged():void{
    sessionStorage.setItem('logged',JSON.stringify(true));
    this.isloggedObservable.next(true);
  }

  logout():void{
    this.isloggedObservable.next(false);
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('logged');
  }
  
  login(userLogin:any):Observable<responsePost>{
    return this.http.post<responsePost>('http://localhost:8080/api/auth/cliente/loginUser',userLogin,{headers:this.httpHeaders});
  }
}
