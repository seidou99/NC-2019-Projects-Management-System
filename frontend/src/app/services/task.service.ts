import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Task} from '../models/task';
import {tasksURI} from '../configs/conf';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient, private auth: AuthService) {
  }

  createTask(task: Task): Observable<Task> {
    task.reporter = this.auth.getUser();
    if (!task.assignee) {
      task.assignee = task.reporter;
    }
    return this.http.post<Task>(tasksURI, task);
  }

  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(tasksURI).pipe(
      map((tasks: Task[]) => {
        tasks.forEach((task: Task) => {
          task.created = new Date(task.created);
          task.dueDate = new Date(task.dueDate);
          if (task.updated) {
            task.updated = new Date(task.updated);
          }
        });
        return tasks;
      })
    );
  }
}
