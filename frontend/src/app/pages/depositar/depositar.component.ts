import { Component } from '@angular/core';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-depositar',
  standalone: true,
  imports: [MenuComponent, CommonModule, RouterModule],
  templateUrl: './depositar.component.html',
  styleUrl: './depositar.component.css'
})
export class DepositarComponent {

}
