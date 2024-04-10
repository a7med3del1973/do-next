import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private taskURL = '/task';

  constructor(
    private api: ApiService,
    private http: HttpClient
  ) { }

  getAll() {
    return this.api.getAll(`${this.taskURL}`);
  }

  getById(id: number) {
    return this.api.getById(`${this.taskURL}/${id}`);
  }

  add(data: any) {
    return this.api.add(`${this.taskURL}`, data);
  }

  update(id: number, data: any) {
    return this.api.update(`${this.taskURL}/${id}`, data);
  }

  delete(id: number) {
    return this.api.delete(`${this.taskURL}/${id}`);
  }

  toggleComplete(id: number): Observable<any> {
    return this.http.post(`${Environment.API_URL}${this.taskURL}/toggle-complete/${id}`, {}, this.api.getHttpOptions());
  }
  
}
