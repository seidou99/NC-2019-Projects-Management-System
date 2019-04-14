import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TaskService} from '../../services/task.service';
import {Task} from '../../models/task';
import {BehaviorSubject, Subject} from 'rxjs';
import {TaskStatus} from '../../models/task-status';
import {CommentService} from '../../services/comment.service';
import {Comment} from '../../models/comment';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-task-dashboard',
  templateUrl: './task-dashboard.component.html',
  styleUrls: ['./task-dashboard.component.css']
})
export class TaskDashboardComponent implements OnInit {

  taskName = '';
  task: Task;
  comments: Comment[] = [];
  task$ = new Subject<Task>();
  isFormDisabled$ = new BehaviorSubject<boolean>(true);

  constructor(private route: ActivatedRoute, private taskService: TaskService, private commentService: CommentService,
              private authService: AuthService) {
  }

  ngOnInit() {
    const taskId = this.route.snapshot.paramMap.get('taskId');
    this.taskService.getTaskById(taskId).subscribe((task: Task) => {
      this.task = task;
      this.task$.next(task);
      this.taskName = `${task.project.code}-${task.code}`;
    }, (e: Error) => console.log(e));
    this.commentService.getAllCommentsByTaskId(taskId).subscribe((comments: Comment[]) => this.comments = comments,
      (e: Error) => console.log(e));
  }

  submitComment(comment: Comment) {
    this.commentService.createComment(comment, this.route.snapshot.paramMap.get('taskId')).subscribe(
      () => {
      }, (e: Error) => console.log(e));
  }

  changeStatus(status: TaskStatus) {
    if (this.task) {
      this.task.status.id = null;
      this.task.status.name = status;
      this.submitTask(this.task);
    }
  }

  submitTask(task: Task) {
    this.taskService.updateTask(task).subscribe((data: Task) => {
      this.ngOnInit();
      this.isFormDisabled$.next(true);
    }, (e: Error) => console.log(e));
  }

  onEditClick() {
    this.isFormDisabled$.next(!this.isFormDisabled$.value);
  }
}
