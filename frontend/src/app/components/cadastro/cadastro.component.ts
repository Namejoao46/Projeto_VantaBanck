import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.css'
})
export class CadastroComponent {
login: string = '';
senha: string = '';
tipo: string = 'CLIENTE';

constructor(private authService: AuthService){}

registrar(){
  const usuario = { login: this.login, senha: this.senha, tipo: this.tipo};
  this.authService.cadastrar(usuario).subscribe({
    next: () => alert('Cadastro realizado com sucesso!'),
    error: (err) => alert('Erro ao cadastrar: ' + err.error)
  });
}
}
