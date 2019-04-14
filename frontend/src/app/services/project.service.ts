import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Project} from '../models/project';
import {projectsURI} from '../configs/conf';
import {Observable} from 'rxjs';
import {Page} from '../models/page';
import {Task} from '../models/task';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient) {
  }

  createProject(project: Project): Observable<any> {
    return this.http.post(projectsURI, project);
  }

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(projectsURI);
  }
}
