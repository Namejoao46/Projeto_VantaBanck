import { Component } from '@angular/core';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { ServicosComponent } from '../../components/clienteHome/servicos/servicos.component';
import { SaldoComponent } from '../../components/clienteHome/saldo/saldo.component';
import { CartaoComponent } from '../../components/clienteHome/cartao/cartao.component';
import { ExtratoComponent } from '../../components/clienteHome/extrato/extrato.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-cliente-home',
  standalone: true,
  imports: [MenuComponent, ServicosComponent, SaldoComponent, CartaoComponent, ExtratoComponent, RouterModule],
  templateUrl: './cliente-home.component.html',
  styleUrl: './cliente-home.component.css'
})
export class ClienteHomeComponent {

}
