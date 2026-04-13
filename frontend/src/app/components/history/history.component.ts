import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService, PurchaseHistory } from '../../services/order.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnInit {
  history: PurchaseHistory[] = [];
  loading = false;
  error = '';

  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit(): void {
    this.loading = true;
    this.orderService.getPurchaseHistory().subscribe({
      next: (data) => { this.history = data; this.loading = false; },
      error: (err) => { this.error = err.error?.message || 'Error al cargar historial'; this.loading = false; }
    });
  }

  goBack(): void {
    this.router.navigate(['/vehicles']);
  }
}