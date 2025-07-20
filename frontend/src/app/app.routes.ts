import { Routes } from '@angular/router';
import { CadastroPagComponent } from './pages/cadastro-pag/cadastro-pag.component';
import { LoginPagComponent } from './pages/login-pag/login-pag.component';
import { HomeComponent } from './pages/home/home.component';
import { ClienteHomeComponent } from './pages/cliente-home/cliente-home.component';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'login-pag', component: LoginPagComponent },
    { path: 'cadastro-pag', component:CadastroPagComponent },
    { path: 'home', component: HomeComponent },
    { path: '', redirectTo: 'home', pathMatch: 'full'},
    { path: '', redirectTo: 'cliente-home', pathMatch: 'full'},
    { path: 'cliente-home', component:ClienteHomeComponent}
];
