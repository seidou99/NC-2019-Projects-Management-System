import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Task} from '../models/task';
import {tasksURI} from '../configs/conf';
import {Observable} from 'rxjs';
import {prepareEnumForServer, processEnumFromServer} from '../util/process-enum';
import {TaskPriority} from '../models/task-priority';
import {TaskStatus} from '../models/task-status';
import {User} from '../models/user';
import {AuthService} from './auth.service';

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
    return this.http.get<Task[]>(tasksURI);
  }
}
