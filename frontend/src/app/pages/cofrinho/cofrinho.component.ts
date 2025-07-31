import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { RouterModule } from '@angular/router';
import { CofrinhoDTO } from '../../models/CofrinhoDTO';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-cofrinho',
  standalone: true,
  imports: [CommonModule, FormsModule, MenuComponent, RouterModule],
  templateUrl: './cofrinho.component.html',
  styleUrl: './cofrinho.component.css'
})
export class CofrinhoComponent {
  dto: CofrinhoDTO = { nome: '', imagem: '', valor: 0 };
  cofrinhos: any[] = [];

  constructor(private http: HttpClient){}

  ngOnInit(): void {
    this.buscarCofrinhos();
  }
  carregarImagem(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = () => this.dto.imagem = reader.result as string;
    reader.readAsDataURL(file);
  }

  criarCofrinho(): void {
    this.http.post('/api/cofrinhos', this.dto).subscribe({
      next: () => {
        alert('Cofrinho criado com sucesso!');
        this.dto = { nome: '', imagem: '', valor: 0};
      },
      error: err => {
        console.error('Erro ao criar cofrinho:', err);
        alert('Erro ao criar cofrinho');
      }
    });
  }

  buscarCofrinhos(): void {
    this.http.get<any[]>('/api/cofinhos').subscribe({
      next: data => this.cofrinhos = data,
      error: err => console.error('Erro ao buscar cofrinhos:', err)
    });
  }
}
