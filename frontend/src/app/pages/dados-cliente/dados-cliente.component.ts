import { Component } from '@angular/core';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { RouterModule } from '@angular/router';
import { ChavePixDTO } from '../../models/chave-pix-dto';
import { PixService } from '../../services/pix.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DadosClienteCompletoDTO } from '../../models/DadosClienteCompletoDTO';

@Component({
  selector: 'app-dados-cliente',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule,MenuComponent],
  templateUrl: './dados-cliente.component.html',
  styleUrl: './dados-cliente.component.css'
})
export class DadosClienteComponent {
    constructor(private pixService: PixService){}
  
    minhaChave: ChavePixDTO | null = null;
    dadosCliente: DadosClienteCompletoDTO | null = null;
    token: string = localStorage.getItem('token') || '';
  
    ngOnInit(){
      this.pixService.consultarMinhaChave(this.token).subscribe({
        next: chave => this.minhaChave = chave,
        error: () => this.minhaChave = null
      });

      this.pixService.consultarDadosCadastrais(this.token).subscribe({
        next: dados => this.dadosCliente = dados,
        error: () => this.dadosCliente = null
      });
    }

    get meuEmail(): string {
      return this.dadosCliente?.email ?? '';
    }

    get dadosConta() {
      return{
        nome: this.dadosCliente?.nome ?? '',
        banco: this.dadosCliente?.banco ?? '',
        agencia: this.dadosCliente?.agencia ?? '',
        numeroConta: this.dadosCliente?.numeroConta ?? ''
      };
    }
}
