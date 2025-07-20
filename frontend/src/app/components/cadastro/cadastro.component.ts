import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ClienteService } from '../../services/cliente-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.css'
})
export class CadastroComponent {
  login: string = '';
  senha: string = '';
  funcao: string = 'CLIENTE';

  nome: string = '';
  cpf: string = '';
  telefone: string = '';
  salario: number = 0;
  dataNascimento: string = '';
  rua: string = '';
  numero: string = '';
  bairro: string = '';
  cidade: string = '';
  estado: string = '';
  cep: string = '';

constructor(
  private authService: AuthService,
  private clienteService: ClienteService,
  private router: Router 
){}

  registrar(){
    console.log('📤 Preparando dados para cadastro...');

    const cliente = { 
      nome: this.nome, 
      cpf: this.cpf,
      telefone: this.telefone,
      salario: this.salario,
      funcao: this.funcao,
      dataNascimento: this.dataNascimento,
      usuario: {
        login: this.login, 
        senha: this.senha,
      },
      endereco: {
        rua: this.rua,
        numero: this.numero,
        bairro: this.bairro,
        cidade: this.cidade,
        estado: this.estado,
        cep: this.cep
      }
    };

    console.log('📡 Chamando cadastrarCliente...');

    this.clienteService.cadastrarCliente(cliente).subscribe({
      next: (resposta) => {
          console.log('✅ Resposta do backend:', resposta);
          alert('Cadastro realizado com sucesso!');
          this.router.navigate(['/login-pag']);
      },
      error: (err) => {
        const mensagem = err.error?.message || 'Erro ao cadastrar';
        alert('❌ ' + mensagem);
        console.error('🔍 Erro completo:', err);
      }
    });
  }
}
