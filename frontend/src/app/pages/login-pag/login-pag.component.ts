import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login-pag',
  imports: [FormsModule, RouterModule],
  templateUrl: './login-pag.component.html',
  styleUrl: './login-pag.component.css'
})
export class LoginPagComponent {
  login: string = '';
    senha: string = '';
  
    constructor(private authService: AuthService, private router: Router) {}
  
    logar(){
      this.authService.login(this.login, this.senha).subscribe({
        next: (res) => {
          localStorage.setItem('token', res.token);
          alert('Login realizado com sucesso!');
          this.router.navigate(['/cliente-home']);
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
