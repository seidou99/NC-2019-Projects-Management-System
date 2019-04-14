import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tasksURI} from '../configs/conf';
import {Comment} from '../models/comment';
import {AuthService} from './auth.service';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  getAllCommentsByTaskId(taskId: string): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${tasksURI}/${taskId}/comments`);
  }

  createComment(comment: Comment, taskId: string): Observable<any> {
    comment.author = new User(null, null, this.authService.getUserRole(), this.authService.getUserEmail(), null);
    return this.http.post(`${tasksURI}/${taskId}/comments`, comment);
  }
}
