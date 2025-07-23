export interface TransacaoDTO {
  tipo: string;
  valor: number;
  dataHora: string; // a data chega como texto no JSON (ex: "2025-07-21T10:00:00")
}
