import { Component } from '@angular/core';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { RouterModule } from '@angular/router';
import { TransferenciaDTO } from '../../models/TransferenciaDTO';
import { TedService } from '../../services/ted.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PixService } from '../../services/pix.service';
import { ChavePixDTO } from '../../models/chave-pix-dto';

@Component({
  selector: 'app-transferir',
  standalone: true,
  imports: [MenuComponent, CommonModule, RouterModule, FormsModule],
  templateUrl: './transferir.component.html',
  styleUrl: './transferir.component.css'
})
export class TransferirComponent {

  constructor(private pixService: PixService){}
  
  minhaChave: ChavePixDTO | null = null;
  token: string = localStorage.getItem('token') || '';
  
  ngOnInit(){
    this.pixService.consultarMinhaChave(this.token).subscribe({
      next: chave => this.minhaChave = chave,
      error: () => this.minhaChave = null
    });
  }
}
