import { Component } from '@angular/core';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { ServicosComponent } from '../../components/clienteHome/servicos/servicos.component';
import { SaldoComponent } from '../../components/clienteHome/saldo/saldo.component';
import { CartaoComponent } from '../../components/clienteHome/cartao/cartao.component';

@Component({
  selector: 'app-cliente-home',
  imports: [MenuComponent, ServicosComponent, SaldoComponent, CartaoComponent],
  templateUrl: './cliente-home.component.html',
  styleUrl: './cliente-home.component.css'
})
export class ClienteHomeComponent {

}
