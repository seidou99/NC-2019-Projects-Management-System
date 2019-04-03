import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Task} from '../models/task';
import {tasksURI} from '../configs/conf';
import {Observable} from 'rxjs';
import {prepareEnumForServer, processEnumFromServer} from '../util/process-enum';
import {TaskPriority} from '../models/task-priority';
import {TaskStatus} from '../models/task-status';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient) {
  }

  createTask(task: Task): Observable<Task> {
    // prepareEnumForServer(task, 'taskPriority', TaskPriority);
    // prepareEnumForServer(task, 'taskStatus', TaskStatus);
    return this.http.post<Task>(tasksURI, task);
  }

  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(tasksURI);
  }
}
