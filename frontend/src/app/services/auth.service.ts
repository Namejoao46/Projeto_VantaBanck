import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {

  }

  login(login: string, senha: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, { login, senha });
  }

  cadastrar(novoUsuario: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, { novoUsuario });
  }
}
