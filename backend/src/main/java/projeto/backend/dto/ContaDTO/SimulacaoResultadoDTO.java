package projeto.backend.dto.ContaDTO;

public class SimulacaoResultadoDTO {
    private double salarioCliente;
    private double limiteDisponivel;
    private double valorSolicitado;
    private int meses;
    private double taxaJurosMensal;
    private double valorFinal;
    private double parcelaMensal;
    private boolean emprestimoAtivo;
    private String faixaSalarial;

    public double getLimiteDisponivel() { return limiteDisponivel; }
    public void setLimiteDisponivel(double limiteDisponivel) { this.limiteDisponivel = limiteDisponivel; }

    public double getSalarioCliente() { return salarioCliente; }
    public void setSalarioCliente(double salarioCliente) {
        this.salarioCliente = salarioCliente; }

    public double getValorSolicitado() {
        return valorSolicitado;
    }
    public void setValorSolicitado(double valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public int getMeses() {
        return meses;
    }
    public void setMeses(int meses) {
        this.meses = meses;
    }

    public double getTaxaJurosMensal() {
        return taxaJurosMensal;
    }
    public void setTaxaJurosMensal(double taxaJurosMensal) {
        this.taxaJurosMensal = taxaJurosMensal;
    }

    public double getValorFinal() {
        return valorFinal;
    }
    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public double getParcelaMensal() {
        return parcelaMensal;
    }
    public void setParcelaMensal(double parcelaMensal) {
        this.parcelaMensal = parcelaMensal;
    }

    public boolean isEmprestimoAtivo() {
        return emprestimoAtivo;
    }
    public void setEmprestimoAtivo(boolean emprestimoAtivo) {
        this.emprestimoAtivo = emprestimoAtivo;
    }

    public String getFaixaSalarial() {
        return faixaSalarial;
    }
    public void setFaixaSalarial(String faixaSalarial) {
        this.faixaSalarial = faixaSalarial;
    }
}