import { Component, OnInit } from '@angular/core';
import { ChavePixDTO } from '../../models/chave-pix-dto';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MenuComponent } from "../../components/clienteHome/menu/menu.component";
import { FormsModule } from '@angular/forms';
import { PixService } from '../../services/pix.service';

@Component({
  selector: 'app-pix-cadastro',
  standalone: true,
  imports: [CommonModule, RouterModule, MenuComponent, FormsModule],
  templateUrl: './pix-cadastro.component.html',
  styleUrl: './pix-cadastro.component.css'
})
export class PixCadastroComponent implements OnInit {
  token: string = '';
  pixDTO: ChavePixDTO = {
    tipo: '',
    valor: ''
  };

  constructor(private pixService: PixService) {}

  ngOnInit(): void{
    this.token = localStorage.getItem('token') || '';
  }

  enviarChavePix(){
    this.pixService.cadastrarChave(this.pixDTO, this.token).subscribe({
      next: () => alert('✅ Chave Pix cadastrada com sucesso!'),
      error: err => alert('❌ Erro: ' + err.error.message)
    });
  }
}
