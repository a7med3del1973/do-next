import { Component, OnInit } from '@angular/core';
import { TaskService } from '../../service/task/task.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrl: './task.component.css'
})
export class TaskComponent implements OnInit {

  task: any = {};

  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    let id = +this.route.snapshot.paramMap.get('id')!;
    this.getTaskDetails(id);
  }

  getTaskDetails(id: number) {
    this.taskService.getById(id).subscribe({
      next: (response: any) => {
        this.task = response.data;
      },
      error: (response: any) => {
        if (response.error.success === false) {
          alert(response.error.error.message);
        }
      }
    });
  }

}
