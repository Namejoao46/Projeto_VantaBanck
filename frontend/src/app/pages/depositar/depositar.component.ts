import { Component } from '@angular/core';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ChavePixDTO } from '../../models/chave-pix-dto';
import { PixService } from '../../services/pix.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-depositar',
  standalone: true,
  imports: [MenuComponent, CommonModule, RouterModule, FormsModule],
  templateUrl: './depositar.component.html',
  styleUrl: './depositar.component.css'
})
export class DepositarComponent {

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
