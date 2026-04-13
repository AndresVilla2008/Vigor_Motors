import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
  console.log('login llamado', this.email, this.password);
  this.loading = true;
  this.error = '';

  console.log('enviando petición...'); // ← agrega esto
  this.authService.login(this.email, this.password).subscribe({
    next: (res) => {
      console.log('respuesta:', res); // ← y esto
      this.router.navigate(['/vehicles']);
    },
    error: (err) => {
      console.log('error:', err); // ← y esto
      this.error = err.error?.message || 'Error al iniciar sesión';
      this.loading = false;
    }
  });
}
}