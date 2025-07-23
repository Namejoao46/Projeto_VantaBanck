import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardDTO } from '../../../models/dashboard-dto';
import { ExtratoService } from '../../../services/extrato.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-saldo',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './saldo.component.html',
  styleUrl: './saldo.component.css'
})
export class SaldoComponent {
  dashboard: DashboardDTO | null = null;

  constructor(private ExtratoService: ExtratoService) {}

  ngOnInit(): void {
    this.ExtratoService.buscarDashboard().subscribe(dados => {
      this.dashboard = dados;
    })
  }
}
