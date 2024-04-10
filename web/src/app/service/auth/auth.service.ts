import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { JwtPayload, jwtDecode } from 'jwt-decode';
import { HttpClient } from '@angular/common/http';
import { Environment } from '../../environment/environment';
import { RegisterRequest } from '../../model/register/register-request';
import { LoginRequest } from '../../model/login/login-request';
import { ApiResponse } from '../../model/api-response/api-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnInit {

  authURL: string = `${Environment.API_URL}/auth`;

  userData = new BehaviorSubject<JwtPayload | null>(null);

  constructor(
    private http: HttpClient,
    private router: Router
  ) { 
    if (this.isLogin()) {
      this.decodeUserData();
    }
  }

  ngOnInit(): void {
    if (localStorage.getItem('token')) {
      this.saveToken();
    }
  }

  isLogin(): boolean {
    return localStorage.getItem('token') != null;
  }

  saveToken() {
    let token = localStorage.getItem('token');
    if (token) {
      let decodedToken = jwtDecode(token);
      this.userData.next(decodedToken);
    }
  }

  decodeUserData() {
    let encodedToken = JSON.stringify(localStorage.getItem('token'));
    let decodedToken: any = jwtDecode(encodedToken);
    this.userData.next(decodedToken);
  }

  login(request: LoginRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.authURL}/login`, request);
  }

  register(request: RegisterRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.authURL}/register`, request);
  }

  verifyEmail(token: string): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.authURL}/verify-email?token=${token}`, null);
  }

  logout() {
    localStorage.removeItem('token');
    this.userData.next(null);
  }

}
