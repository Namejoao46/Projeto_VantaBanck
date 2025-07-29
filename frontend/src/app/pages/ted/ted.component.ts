import { Component } from '@angular/core';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TransferenciaDTO } from '../../models/TransferenciaDTO';
import { TedService } from '../../services/ted.service';

@Component({
  selector: 'app-ted',
  standalone: true,
  imports: [MenuComponent, RouterModule, FormsModule, CommonModule],
  templateUrl: './ted.component.html',
  styleUrl: './ted.component.css'
})
export class TedComponent {
   token: string = '';
    dto: TransferenciaDTO = {
      destinoLogin: '',
      valor: 0,
      senha:''
    };
    erro: string = '';
  
    constructor(private tedService: TedService){}
  
    ngOnInit(): void {
      this.token = localStorage.getItem('token') || '';
    }
  
    enviarTed(): void {
      this.tedService.TransferirTed(this.dto, this.token).subscribe({
        next:() => {
          alert(' Ted realizado com Sucesso!');
          this.erro = '';
          this.dto = { destinoLogin: '', valor: 0, senha: ''};
        },
        error: err => {
          this.erro = err.error.message || 'Erro ao enviar TED';
        }
      });
    }
}
