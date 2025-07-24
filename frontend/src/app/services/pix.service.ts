import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PixDTO } from '../models/PixDTO';

@Injectable({
  providedIn: 'root'
})
export class PixService {
  private baseUrl = 'http://localhost:8080/enviar';
  
  constructor(private http: HttpClient) {}

  enviarPix(dto: PixDTO, token: string): Observable<void>{
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.post<void>(this.baseUrl, dto, { headers });
  }
}
