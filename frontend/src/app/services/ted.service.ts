import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TransferenciaDTO } from '../models/TransferenciaDTO';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TedService {

  constructor(private http: HttpClient) { }

  TransferirTed(dto: TransferenciaDTO, token: string): Observable<void> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.post<void>('http://localhost:8080/api/clientes/transferencia', dto, { headers });
  }
}
