import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Nota } from 'src/app/models/nota';

@Injectable({
  providedIn: 'root'
})
export class NotaServiceService {
  constructor(private http:HttpClient) { }
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json',
                                          'Authorization': 'Bearer '+sessionStorage.getItem('token'),
                                          'Access-Control-Allow-Origin':'*'});


  insertNotas(nota:Nota):Observable<Nota>{
    return this.http.post<Nota>('http://localhost:8080/api/notas/insertNote',nota,{headers:this.httpHeaders,withCredentials:true})
  }
}
