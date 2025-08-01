import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PixDTO } from '../models/PixDTO';
import { ChavePixDTO } from '../models/chave-pix-dto';
import { DadosClienteCompletoDTO } from '../models/DadosClienteCompletoDTO';

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

  cadastrarChave(dto: ChavePixDTO, token: string): Observable<void> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.post<void>('http://localhost:8080/pix/chaves/cadastrar', dto, { headers });
  }

  consultarMinhaChave(token: string): Observable<ChavePixDTO | null>{
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<ChavePixDTO>('http://localhost:8080/pix/chaves/minha', { headers });
  }

  consultarDadosCadastrais(token: string): Observable<DadosClienteCompletoDTO>{
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<DadosClienteCompletoDTO>('http://localhost:8080/cliente/dados', { headers });
  }
}
