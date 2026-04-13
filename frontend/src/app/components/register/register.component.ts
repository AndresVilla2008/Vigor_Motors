import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  error = '';
  success = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  register(): void {
    this.loading = true;
    this.error = '';
    this.success = '';

    this.authService.register(this.username, this.email, this.password).subscribe({
      next: (res) => {
        this.success = res.message;
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: (err) => {
        this.error = err.error?.message || 'Error al registrarse';
        this.loading = false;
      }
    });
  }
}