import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TransacaoDTO } from '../models/transacaoDTO';
import { FiltroTransacaoDTO } from '../models/filtro-transacao-dto';
import { Observable } from 'rxjs';
import { DashboardDTO } from '../models/dashboard-dto';


@Injectable({
  providedIn: 'root'
})
export class ExtratoService {
  baseUrl: any;

  constructor(private http: HttpClient) {}

  buscarExtrato(){
    return this.http.get<TransacaoDTO[]>(`${this.baseUrl}/transacoes`);
  }

  filtroPorData(filtro: FiltroTransacaoDTO): Observable<TransacaoDTO[]> {
    return this.http.post<TransacaoDTO[]>(`${this.baseUrl}/transacoes/filtro`, filtro)
  }

  baixarCSV(): Observable<Blob>{
    return this.http.get(`$this.baseUrl)/transacoes/exportar`,{
      responseType: 'blob'
    });
  }

  baixarPDF(): Observable<Blob>{
    return this.http.get(`$this.baseUrl)/transacoes/exportar/pdf`,{
      responseType: 'blob'
    });
  }

  buscarDashboard(): Observable<DashboardDTO>{
      return this.http.get<DashboardDTO>(`http://localhost:8080/api/clientes/dashboard`);
    }
}
