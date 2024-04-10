import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Environment } from '../../environment/environment';
import { ApiResponse } from '../../model/api-response/api-response';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    })
  };

  constructor(private http: HttpClient) { }

  getHttpOptions() {
    return this.httpOptions;
  }

  getAll(route: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${Environment.API_URL}${route}`, this.httpOptions);
  }

  getById(route: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${Environment.API_URL}${route}`, this.httpOptions);
  }

  add(route: string, data: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${Environment.API_URL}${route}`, data, this.httpOptions);
  }

  update(route: string, data: any): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${Environment.API_URL}${route}`, data, this.httpOptions);
  }

  delete(route: string): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${Environment.API_URL}${route}`, this.httpOptions);
  }
}
