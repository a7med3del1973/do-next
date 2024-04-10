import { Component } from '@angular/core';
import { TaskService } from '../../service/task/task.service';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../../service/category/category.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  tasks: any[] = [];
  addFlag: boolean = true;
  categories: any[] = [];
  taskForm!: FormGroup;

  constructor(
    private taskService: TaskService,
    private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder,
    private categoryService: CategoryService
  ) { 
    if (!this.authService.isLogin()) {
      this.router.navigate(['/login']);
    }
  }

  ngOnInit() {
    this.getAllTasks();
    this.getAllCategories();
    this.taskForm = this.formBuilder.group({
      id: [null],
      title: ['', [Validators.required]],
      description: [''],
      completed: [false],
      dueDate: ['', [Validators.required]],
      categoryId: [0]
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  getAllTasks() {
    this.taskService.getAll().subscribe({
      next: (response) => {
        this.tasks = response.data;
      },
      error: (response) => {
        if (response.error.success === false) {
          alert(response.error.error.message)
        }
      }
    });
  }

  getAllCategories() {
    this.categoryService.getAll().subscribe({
      next: (response) => {
        this.categories = response.data;
      },
      error: (response) => {
        if (response.error.success === false) {
          alert(response.error.error.message)
        }
      }
    });
  }

  onAdd() {
    this.addFlag = true;
    this.taskForm = this.formBuilder.group({
      id: [null],
      title: ['', [Validators.required]],
      description: [''],
      completed: [false],
      dueDate: ['', [Validators.required]],
      categoryId: [0]
    });
  }

  addTask() {
    this.taskService.add(this.taskForm.value).subscribe({
      next: (response) => {
        this.tasks.push(response.data);
        alert("Task added successfully.");
      },
      error: (response) => {
        if (response.error.success === false) {
          alert(response.error.error.message)
        }
      }
    });
  }

  onEdit(task: any) {
    this.addFlag = false;
    this.taskForm.controls['categoryId'].setValue(task.categoryId !== undefined? task.categoryId : 0);
    this.taskForm.patchValue(task);
  }

  updateTask() {
    let taskValue = this.taskForm.value;
    this.taskService.update(taskValue.id, taskValue).subscribe({
      next: (response) => {
        const index = this.tasks.findIndex((task) => task.id === response.data.id);
        this.tasks[index] = response.data;
        alert("Task updated successfully.");
      },
      error: (response) => {
        if (response.error.success === false) {
          alert(response.error.error.message)
        }
      }
    });
  }

  deleteTask(id: number) {
    this.taskService.delete(id).subscribe({
      next: (response) => {
        this.tasks = this.tasks.filter((task) => task.id !== id);
      },
      error: (response) => {
        if (response.error.success === false) {
          alert(response.error.error.message)
        }
      }
    });
  }

  confirmDelete(id: number) {
    if (window.confirm('Are you sure you want to delete this task?')) {
      this.deleteTask(id);
    }
  }

  toggleComplete(id: number) {
    this.taskService.toggleComplete(id).subscribe({
      next: (response) => {
        console.log(response.data);
      },
      error: (response) => {
        if (response.error.success === false) {
          alert(response.error.error.message)
        }
      }
    });
  }

}
