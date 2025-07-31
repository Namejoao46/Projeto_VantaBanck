import { Injectable } from '@angular/core';
import { SaqueDTO } from '../models/saqueDTO';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SaqueService {

  constructor(private http: HttpClient) { }

  sacar(valor: SaqueDTO, token: string): Observable<void>{
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.post<void>('http://localhost:8080/api/clientes/saque', valor, { headers });
  }
}
