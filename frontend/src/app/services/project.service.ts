import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Project} from '../models/project';
import {projectsURI} from '../configs/conf';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient) {
  }

  createProject(project: Project): Observable<Project> {
    return this.http.post<Project>(projectsURI, project);
  }

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(projectsURI);
  }
}
