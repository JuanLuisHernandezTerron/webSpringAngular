import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from 'src/app/models/usuario';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import {responsePost} from "./../../models/responsePost";
@Injectable({
  providedIn: 'root'
})
export class AuthServicesService implements OnInit{
  constructor(private http: HttpClient) { 
  }
  ngOnInit(): void {
    
  }
  public isloggedObservable = new BehaviorSubject<boolean>(JSON.parse(sessionStorage.getItem('logged')));
  isLogged$= this.isloggedObservable.asObservable();
  
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  
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
