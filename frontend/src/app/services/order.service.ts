import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface SelectedItem {
  itemId: number;
  quantity: number;
  price: number;
  vehicleId: number;
  brand: string;
  model: string;
  year: number;
  color: string;
  fuelType: string;
  transmission: string;
  imageUrl: string;
}

export interface PurchaseHistory {
  totalPrice: number;
  status: string;
  brand: string;
  model: string;
  year: number;
  color: string;
  fuelType: string;
  transmission: string;
  imageUrl: string;
  quantity: number;
  price: number;
}

export interface PurchaseHistoryAdmin extends PurchaseHistory {
  userId: number;
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = '/api/ordersItems';

  constructor(private http: HttpClient) {}

  selectProducts(vehicleIds: number[]): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/selectProduct`, { vehicleIds });
  }

  purchase(): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/purchase`, {});
  }

  getSelectedProducts(): Observable<SelectedItem[]> {
    return this.http.get<SelectedItem[]>(`${this.apiUrl}/viewSelectedProducts`);
  }

  deleteItem(itemId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/deleteItem/${itemId}`);
  }

  getPurchaseHistory(): Observable<PurchaseHistory[]> {
    return this.http.get<PurchaseHistory[]>(`${this.apiUrl}/purchaseHistory`);
  }

  getAllPurchaseHistory(): Observable<PurchaseHistoryAdmin[]> {
    return this.http.get<PurchaseHistoryAdmin[]>(`${this.apiUrl}/admin/purchaseHistory`);
  }
}