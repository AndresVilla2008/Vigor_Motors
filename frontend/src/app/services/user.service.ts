import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface ChangeRoleRequest {
  email: string;
  role: 'ADMIN' | 'CLIENT';
}

export interface MessageResponse {
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = '/api/users';

  constructor(private http: HttpClient) {}

  changeRole(request: ChangeRoleRequest): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.apiUrl}/changeRole`, request);
  }
}