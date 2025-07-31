export interface SimulacaoResultadoDTO {
  salarioCliente: number;
  limiteDisponivel: number;
  valorSolicitado: number;
  meses: number;
  taxaJurosMensal: number;
  valorFinal: number;
  parcelaMensal: number;
  temEmprestimoAtivo: boolean;
  faixaSalario: string;
}

export interface SimulacaoRequestDTO {
  valorSolicitado: number;
  meses: number;
}