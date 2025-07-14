import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  login: string = '';
  senha: string = '';

  constructor(private authService: AuthService){

  }

  logar(){
    this.authService.login(this.login, this.senha).subscribe({
      next: (res) => {
        localStorage.setItem('token', res.token);
        alert('Login realizado com sucesso!');

      },
      error: (err) => {
        console.error('Erro completo:', err);
        if(err.status === 0) {
          alert('Não foi possivel conectar ao servidor. Verifique se o backend está rodando.');
        } else if (err.error && typeof err.error === 'string'){
          alert('Falha no login: ' + err.error);
        } else {
          alert('Falha no login. Código: ' + err.status);
        }
        
      }
    })
  }
}
