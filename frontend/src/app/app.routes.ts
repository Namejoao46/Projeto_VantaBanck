import { Routes } from '@angular/router';
import { CadastroPagComponent } from './pages/cadastro-pag/cadastro-pag.component';
import { LoginPagComponent } from './pages/login-pag/login-pag.component';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'login-pag', component: LoginPagComponent },
    { path: 'cadastro-pag', component:CadastroPagComponent }
];
