import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Task} from '../models/task';
import {projectsURI} from '../configs/conf';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';
import {map} from 'rxjs/operators';
import {Page} from '../models/page';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  createTask(task: Task): Observable<any> {
    task.reporter = new User(null, null, this.authService.getUserRole(), this.authService.getUserEmail(), null);
    if (!task.assignee) {
      task.assignee = task.reporter;
    }
    return this.http.post(`${projectsURI}/${task.project.id}/tasks`, task);
  }

  processTaskDates(task: Task): Task {
    console.log(task);
    task.created = new Date(task.created);
    task.dueDate = new Date(task.dueDate);
    if (task.updated) {
      task.updated = new Date(task.updated);
    }
    if (task.resolved) {
      task.resolved = new Date(task.resolved);
    }
    if (task.closed) {
      task.closed = new Date(task.closed);
    }
    return task;
  }

  getTasksPage(projectId: string, pageNumber: number, pageSize: number): Observable<Page<Task>> {
    const params = new HttpParams()
      .set('page', `${pageNumber - 1}`)
      .set('size', `${pageSize}`);
    return this.http.get<Page<Task>>(`${projectsURI}/${projectId}/tasks`, {params});
  }

  updateTask(task: Task): Observable<Task> {
    task.priority.id = null;
    task.status.id = null;
    return this.http.put<Task>(`${projectsURI}/${task.project.id}/tasks/${task.id}`, task);
  }

  getTask(projectId: string, taskId: string): Observable<Task> {
    return this.http.get<Task>(`${projectsURI}/${projectId}/tasks/${taskId}`).pipe(
      map((v: Task) => this.processTaskDates(v))
    );
  }
}
