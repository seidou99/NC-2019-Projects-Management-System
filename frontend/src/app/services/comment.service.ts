import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {projectsURI} from '../configuration/config';
import {Comment} from '../models/comment';
import {AuthService} from './auth.service';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  getAllTaskComments(projectId: string, taskId: string): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${projectsURI}/${projectId}/tasks/${taskId}/comments`);
  }

  createComment(projectId: string, taskId: string, comment: Comment): Observable<any> {
    comment.author = {id: this.authService.getUserId()} as User;
    return this.http.post(`${projectsURI}/${projectId}/tasks/${taskId}/comments`, comment);
  }
}
