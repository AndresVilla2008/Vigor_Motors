import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService, SelectedItem } from '../../services/order.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  items: SelectedItem[] = [];
  loading = false;
  error = '';
  success = '';

  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.loading = true;
    this.error = '';
    this.orderService.getSelectedProducts().subscribe({
      next: (data) => { this.items = data; this.loading = false; },
      error: (err) => {
        const status = err?.status;
        if (status === 404 || status === 400) {
          this.items = [];
        } else {
          this.error = 'Error al cargar el carrito';
        }
        this.loading = false;
      }
    });
  }

  get total(): number {
    return this.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  deleteItem(itemId: number): void {
    this.orderService.deleteItem(itemId).subscribe({
      next: () => this.loadCart(),
      error: (err) => this.error = err.error?.message || 'Error al eliminar'
    });
  }

  purchase(): void {
    this.orderService.purchase().subscribe({
      next: () => {
        this.success = 'Compra realizada exitosamente';
        setTimeout(() => this.router.navigate(['/history']), 1500);
      },
      error: (err) => this.error = err.error?.message || 'Error al realizar la compra'
    });
  }

  goBack(): void {
    this.router.navigate(['/vehicles']);
  }
}