import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private categoryURL = '/category';

  constructor(private api: ApiService) { }

  getAll() {
    return this.api.getAll(`${this.categoryURL}`);
  }

  getById(id: number) {
    return this.api.getById(`${this.categoryURL}/${id}`);
  }

  add(data: any) {
    return this.api.add(`${this.categoryURL}`, data);
  }

  update(data: any) {
    return this.api.update(`${this.categoryURL}`, data);
  }

  delete(id: number) {
    return this.api.delete(`${this.categoryURL}/${id}`);
  }
  
}
