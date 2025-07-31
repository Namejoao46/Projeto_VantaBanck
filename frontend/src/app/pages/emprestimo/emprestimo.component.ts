import { Component } from '@angular/core';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SimulacaoResultadoDTO } from '../../models/emprestimoDTO';
import { EmprestimoService } from '../../services/emprestimo.service';

@Component({
  selector: 'app-emprestimo',
  standalone: true,
  imports: [MenuComponent, CommonModule, FormsModule, RouterModule],
  templateUrl: './emprestimo.component.html',
  styleUrl: './emprestimo.component.css'
})
export class EmprestimoComponent {
  valorSolicitado = 0;
  meses = 12;
  resultado?: SimulacaoResultadoDTO;

  constructor(private emprestimoService: EmprestimoService){}

  simular(){
    const dto = { valorSolicitado: this.valorSolicitado, meses: this.meses 
    };
    this.emprestimoService.simular(dto).subscribe(res => this.resultado = res);
  }

  aceitar(){
    if(!this.resultado) return;
    const dto = { valorSolicitado: this.resultado.valorSolicitado, meses: this.resultado.meses };
    this.emprestimoService.aceitar(dto).subscribe(() => {
      alert(' Emprestimo contratado com sucesso!');
    });
  }

}
