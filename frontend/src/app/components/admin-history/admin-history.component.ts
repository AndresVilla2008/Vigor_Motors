import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService, PurchaseHistoryAdmin } from '../../services/order.service';

@Component({
  selector: 'app-admin-history',
  templateUrl: './admin-history.component.html',
  styleUrls: ['./admin-history.component.scss']
})
export class AdminHistoryComponent implements OnInit {
  history: PurchaseHistoryAdmin[] = [];
  loading = false;
  error = '';

  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit(): void {
    this.loading = true;
    this.orderService.getAllPurchaseHistory().subscribe({
      next: (data) => { this.history = data; this.loading = false; },
      error: (err) => { this.error = err.error?.message || 'Error al cargar historial'; this.loading = false; }
    });
  }

  goBack(): void {
    this.router.navigate(['/vehicles']);
  }
}