import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoginPagComponent } from "./pages/login-pag/login-pag.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LoginPagComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
