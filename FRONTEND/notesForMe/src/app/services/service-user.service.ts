import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario';
@Injectable({
  providedIn: 'root'
})
export class ServiceUserService {
  constructor(private http: HttpClient) { }

  private url:string = "http://localhost:8080/api/";

  /*getUsuarios():Observable<Usuario[]>{
    return this.http.get<Usuario[]>(this.url);
  }*/
}
