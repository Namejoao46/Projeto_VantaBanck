import { Component } from '@angular/core';
import { PixService } from '../../services/pix.service';
import { PixDTO } from '../../models/PixDTO';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MenuComponent } from "../../components/clienteHome/menu/menu.component";
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-pix',
  standalone: true,
  imports: [CommonModule, RouterModule, MenuComponent, FormsModule],
  templateUrl: './pix.component.html',
  styleUrl: './pix.component.css'
})
export class PixComponent {
  pixDTO: PixDTO = {
    chaveDestino: '',
    valor: 0,
    descricao: '',
    senha: ''
  };

  token: string = "";

  constructor(private pixService: PixService) {}

  enviarPix() {
    this.pixService.enviarPix(this.pixDTO, this.token).subscribe({
      next: () => alert('✅ Pix enviado com sucesso!'),
      error: err => alert('❌ Falha ao enviar Pix: ' + err.error.message)
    });
  }

}
