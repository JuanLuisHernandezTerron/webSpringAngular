import { Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Nota } from 'src/app/models/nota';

@Injectable({
  providedIn: 'root'
})
export class NotaServiceService {
  constructor(private http: HttpClient) {
  }
  private httpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + sessionStorage.getItem('token'),
    'Access-Control-Allow-Origin': '*'
  });
  private ListaNotas: Array<Nota> = null;
  public ListaNotasObservable = new BehaviorSubject<Array<Nota>>(this.ListaNotas);
  public ListaNotas$ = this.ListaNotasObservable.asObservable();

  insertNotas(nota: Nota): Observable<Nota> {
    return this.http.post<Nota>('http://localhost:8080/api/notas/insertNote', nota, { headers: this.httpHeaders, withCredentials: true })
  }

  infoNotas(idusuario: String): void {
    this.http.get<Array<Nota>>('http://localhost:8080/api/notas/getNotasUser/' + idusuario, { headers: this.httpHeaders, withCredentials: true }).subscribe((nota) => {
      this.ListaNotasObservable.next(nota);
    })
  }

  deleteNota(idusuario: Number): Observable<any> {
    return this.http.delete<any>('http://localhost:8080/api/notas/deleteNote/' + idusuario, { headers: this.httpHeaders, withCredentials: true })
  }

  insertImageNota(img:FormData):Observable<any> {
    let httpHeadersAuth = new HttpHeaders(
      {
        'Authorization': 'Bearer ' + sessionStorage.getItem('token'),
        'Access-Control-Allow-Origin': '*'
      }
    );
    return this.http.post<any>('http://localhost:8080/api/notas/insertIMG',img,{ headers: httpHeadersAuth });
  }

  NotaBorrada(nota: Nota): Observable<any> {
    let httpHeadersAuth = new HttpHeaders(
      {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + sessionStorage.getItem('token'),
        'Access-Control-Allow-Origin': '*'
      }
    );
    return this.http.put<any>("http://localhost:8080/api/notas/updateNotaBorrada", nota, { headers: httpHeadersAuth });
  }

  actualizarNota(notaActualizar:Nota):Observable<any>{
    let httpHeadersAuth = new HttpHeaders(
      {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + sessionStorage.getItem('token'),
        'Access-Control-Allow-Origin': '*'
      }
    );
    return this.http.put<any>("http://localhost:8080/api/notas/updateNota/"+notaActualizar.id,notaActualizar,{headers:httpHeadersAuth})
  }

  busquedaAvanzada(informacion:any):Observable<any>{
    let httpHeadersAuth = new HttpHeaders(
      {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + sessionStorage.getItem('token'),
        'Access-Control-Allow-Origin': '*'
      }
    );

    return this.http.get<any>("http://localhost:8080/api/notas/busquedaAvanzada",{headers:httpHeadersAuth,params:informacion})
  }
}
