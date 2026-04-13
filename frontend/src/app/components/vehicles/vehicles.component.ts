import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { VehicleService, RegisterVehicleRequest } from '../../services/vehicle.service';
import { OrderService } from '../../services/order.service';
import { Vehicle } from '../../interfaces/vehicle.interface';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.scss']
})
export class VehiclesComponent implements OnInit {
  vehicles: Vehicle[] = [];
  isAdmin = false;
  loading = false;
  error = '';
  success = '';

  showModal = false;
  editingId: number | null = null;
  form: RegisterVehicleRequest = this.emptyForm();

  // Carrito
  cartIds: number[] = [];

  constructor(
    private vehicleService: VehicleService,
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.checkRole();
    this.loadVehicles();
  }

  checkRole(): void {
    const token = this.authService.getToken();
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      this.isAdmin = payload.role === 'ADMIN';
    }
  }

  loadVehicles(): void {
    this.loading = true;
    this.vehicleService.getAll().subscribe({
      next: (data) => {
        this.vehicles = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar los vehículos';
        this.loading = false;
      }
    });
  }

  openCreate(): void {
    this.editingId = null;
    this.form = this.emptyForm();
    this.showModal = true;
  }

  openEdit(vehicle: Vehicle): void {
    this.editingId = vehicle.id;
    this.form = {
      brand: vehicle.brand,
      model: vehicle.model,
      year: vehicle.year,
      price: vehicle.price,
      color: vehicle.color,
      fuelType: vehicle.fuelType,
      transmission: vehicle.transmission,
      mileage: vehicle.mileage,
      description: vehicle.description,
      imageUrl: vehicle.imageUrl
    };
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  save(): void {
    if (this.editingId) {
      this.vehicleService.update(this.editingId, this.form).subscribe({
        next: (res) => {
          this.success = res.message;
          this.closeModal();
          this.loadVehicles();
        },
        error: (err) => this.error = err.error?.message || 'Error al actualizar'
      });
    } else {
      this.vehicleService.create(this.form).subscribe({
        next: (res) => {
          this.success = res.message;
          this.closeModal();
          this.loadVehicles();
        },
        error: (err) => this.error = err.error?.message || 'Error al crear'
      });
    }
  }

  delete(id: number): void {
    if (!confirm('¿Estás seguro de eliminar este vehículo?')) return;
    this.vehicleService.delete(id).subscribe({
      next: (res) => {
        this.success = res.message;
        this.loadVehicles();
      },
      error: (err) => this.error = err.error?.message || 'Error al eliminar'
    });
  }

  toggleCart(id: number): void {
    const index = this.cartIds.indexOf(id);
    if (index === -1) {
      this.cartIds.push(id);
    } else {
      this.cartIds.splice(index, 1);
    }
  }

  isInCart(id: number): boolean {
    return this.cartIds.includes(id);
  }

  goToCart(): void {
  if (this.cartIds.length === 0) {
    this.error = 'No has seleccionado ningún vehículo';
    return;
  }
  console.log('cartIds:', this.cartIds); // ← agrega esto
  this.orderService.selectProducts(this.cartIds).subscribe({
    next: () => this.router.navigate(['/cart']),
    error: (err) => {
      console.log('error completo:', err); // ← y esto
      this.error = err.error?.message || 'Error al seleccionar productos';
    }
  });
}

  logout(): void {
    this.authService.logout().subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => {
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
      }
    });
  }

  private emptyForm(): RegisterVehicleRequest {
    return {
      brand: '', model: '', year: 2024, price: 0,
      color: '', fuelType: 'Gasolina', transmission: 'Automática',
      mileage: 0, description: '', imageUrl: ''
    };
  }
}