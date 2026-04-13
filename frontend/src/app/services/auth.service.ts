import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Observable, tap } from 'rxjs';

export interface LoginResponse {
  message: string;
  jwt: string;
}

export interface MessageResponse {
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = '/api/auth';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap((resp) => {
        if (resp.jwt) {
          this.saveToken(resp.jwt);
        }
      })
    );
  }

  register(username: string, email: string, password: string): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.apiUrl}/register`, { username, email, password });
  }

  logout(): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.apiUrl}/logout`, {}).pipe(
      tap(() => {
        this.removeToken();
      })
    );
  }

  refreshToken(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/refreshToken`).pipe(
      tap((resp) => {
        if (resp.jwt) {
          this.saveToken(resp.jwt);
        }
      })
    );
  }

  saveToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('token', token);
    }
  }

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('token');
    }
    return null;
  }

  removeToken(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
    }
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}