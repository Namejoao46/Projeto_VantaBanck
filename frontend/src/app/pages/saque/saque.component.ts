import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { SaqueService } from '../../services/saque.service';
import { SaqueDTO } from '../../models/saqueDTO';

@Component({
  selector: 'app-saque',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, MenuComponent],
  templateUrl: './saque.component.html',
  styleUrl: './saque.component.css'
})
export class SaqueComponent {
  token: string = '';
  saque: SaqueDTO = {
    valor: 0,
    senha: ''
  };
  erro: string = '';
  loading: boolean = false;

  constructor(private saqueService: SaqueService){}

  ngOnInit(): void {
    this.token = localStorage.getItem('token') || '';
  }

  realizarSaque(): void {
    if(this.saque.valor <10){
      this.erro = 'valor minimo para saque é R$10.';
      return;
    }

    this.loading = true;
    this.saqueService.sacar(this.saque, this.token).subscribe({
      next: () => {
        alert('Saque realizado com sucesso!');
        this.erro = '';
        this.saque = { valor: 0, senha: ''};
      },
      error: err => {
        this.erro = err.error.message || 'Erro ao realizar saque.';
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}
