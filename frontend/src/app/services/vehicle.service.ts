import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vehicle } from '../interfaces/vehicle.interface';

export interface RegisterVehicleRequest {
  brand: string;
  model: string;
  year: number;
  price: number;
  color: string;
  fuelType: string;
  transmission: string;
  mileage: number;
  description: string;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  private apiUrl = '/api/vehicles';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.apiUrl);
  }

  getById(id: number): Observable<Vehicle> {
    return this.http.get<Vehicle>(`${this.apiUrl}/${id}`);
  }

  create(vehicle: RegisterVehicleRequest): Observable<any> {
    return this.http.post<any>(this.apiUrl, vehicle);
  }

  update(id: number, vehicle: RegisterVehicleRequest): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, vehicle);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}