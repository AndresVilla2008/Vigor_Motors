import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-change-role',
  templateUrl: './change-role.component.html',
  styleUrls: ['./change-role.component.scss']
})
export class ChangeRoleComponent {
  email = '';
  role: 'ADMIN' | 'CLIENT' = 'CLIENT';
  error = '';
  success = '';
  loading = false;

  constructor(private userService: UserService, private router: Router) {}

  changeRole(): void {
    this.error = '';
    this.success = '';
    this.loading = true;

    this.userService.changeRole({ email: this.email, role: this.role }).subscribe({
      next: (res: { message: string }) => {
        this.success = res.message;
        this.loading = false;
        this.email = '';
      },
      error: (err: any) => {
        this.error = err.error?.message || 'Error al cambiar el rol';
        this.loading = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/vehicles']);
  }
}