import { Component, OnInit } from '@angular/core';
import { TransacaoDTO } from '../../../models/transacaoDTO';
import { ExtratoService } from '../../../services/extrato.service';
import { CommonModule } from '@angular/common';
import { FiltroTransacaoDTO } from '../../../models/filtro-transacao-dto';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-extrato',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './extrato.component.html',
  styleUrl: './extrato.component.css'
})
export class ExtratoComponent implements OnInit {
  extrato: TransacaoDTO[] = [];
  filtro: FiltroTransacaoDTO = {inicio: '', fim: ''};

  constructor(private extratoService: ExtratoService){}

  ngOnInit(): void {
    this.extratoService.buscarExtrato().subscribe(res => {
      this.extrato = res;
    })
  }

  carregarExtrato(): void {
    this.extratoService.buscarExtrato().subscribe(res => this.extrato = res);
  }

  aplicarFiltro(): void{
    this.extratoService.filtroPorData(this.filtro).subscribe(res => this.extrato = res);
  }

  baixarCSV(): void {
    this.extratoService.baixarCSV().subscribe(blob => {
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = 'extrato.csv';
      link.click();
    })
  }

  baixarPDF(): void {
    this.extratoService.baixarPDF().subscribe(blob => {
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = 'extrato.pdf';
      link.click();
    })
  }
}
