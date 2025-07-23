import { Component } from '@angular/core';
import { MenuComponent } from '../../components/clienteHome/menu/menu.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-transferir',
  standalone: true,
  imports: [MenuComponent, RouterModule],
  templateUrl: './transferir.component.html',
  styleUrl: './transferir.component.css'
})
export class TransferirComponent {

}
