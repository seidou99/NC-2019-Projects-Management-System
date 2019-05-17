import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Project} from '../models/project';
import {projectsURI} from '../configuration/config';
import {Observable} from 'rxjs';
import {Page} from '../models/page';
import {Task} from '../models/task';
import {TaskType} from "../models/task-type";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  createProject(project: Project): Observable<any> {
    return this.http.post(projectsURI, project);
  }

  getAllProjects(taskType: TaskType): Observable<Project[]> {
    let params = new HttpParams();
    switch (taskType) {
      case (TaskType.ASSIGNED): {
        params = params.set('assigneeId', '' + this.authService.getUserId());
        break;
      }
      case (TaskType.REPORTED): {
        params = params.set('reporterId', '' + this.authService.getUserId());
        break;
      }
    }
    return this.http.get<Project[]>(projectsURI, {params});
  }
}
