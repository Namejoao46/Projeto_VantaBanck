import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SimulacaoRequestDTO, SimulacaoResultadoDTO } from '../models/emprestimoDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmprestimoService {
  private baseUrl = '/api/emprestimos';

  constructor(private http: HttpClient) { }

  simular(dto: SimulacaoRequestDTO): Observable<SimulacaoResultadoDTO> {
    return this.http.post<SimulacaoResultadoDTO>(`${this.baseUrl}/simular`, dto);
  }

  aceitar(dto: SimulacaoRequestDTO): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/aceitar`, dto);
  }
}
