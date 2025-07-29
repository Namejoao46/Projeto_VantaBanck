import { Routes } from '@angular/router';
import { CadastroPagComponent } from './pages/cadastro-pag/cadastro-pag.component';
import { LoginPagComponent } from './pages/login-pag/login-pag.component';
import { HomeComponent } from './pages/home/home.component';
import { ClienteHomeComponent } from './pages/cliente-home/cliente-home.component';
import { DepositarComponent } from './pages/depositar/depositar.component';
import { TransferirComponent } from './pages/transferir/transferir.component';
import { PixComponent } from './pages/pix/pix.component';
import { PixCadastroComponent } from './pages/pix-cadastro/pix-cadastro.component';
import { DadosClienteComponent } from './pages/dados-cliente/dados-cliente.component';
import { TedComponent } from './pages/ted/ted.component';

export const routes: Routes = [

    { path: 'home', component: HomeComponent },
    { path: '', redirectTo: 'home', pathMatch: 'full'},

    { path: 'login-pag', component: LoginPagComponent },

    { path: 'cadastro-pag', component:CadastroPagComponent },

    { path: 'cliente-home', 
    component:ClienteHomeComponent },

    { path: 'depositar', component: DepositarComponent },

    { path: 'transferir', component: TransferirComponent },

    { path: 'pix', component: PixComponent},

    { path: 'pixCadastro', component: PixCadastroComponent },

    { path: 'dados-cliente', component: DadosClienteComponent},

    { path: 'ted', component: TedComponent }
];
