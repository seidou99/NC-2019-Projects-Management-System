import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskService} from '../../services/task.service';
import {Task} from '../../models/task';
import {BehaviorSubject, Subject} from 'rxjs';
import {TaskStatus} from '../../models/task-status';
import {CommentService} from '../../services/comment.service';
import {Comment} from '../../models/comment';
import {AuthService} from '../../services/auth.service';
import {AttachmentService} from '../../services/attachment.service';
import {Ng4LoadingSpinnerService} from "ng4-loading-spinner";
import {HttpErrorResponse} from "@angular/common/http";

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
              private attachmentService: AttachmentService, private spinner: Ng4LoadingSpinnerService, private router: Router) {
  }

  ngOnInit() {
    const taskId = this.route.snapshot.paramMap.get('taskId');
    const projectId = this.route.snapshot.paramMap.get('taskId');
    this.spinner.show();
    this.taskService.getTask(projectId, taskId).subscribe((task: Task) => {
      this.task = task;
      this.task$.next(task);
      this.taskName = `${task.project.code}-${task.code}`;
      this.spinner.hide();
    }, (e: HttpErrorResponse) => {
      console.log(e);
      if (e.status === 404) {
        this.router.navigate(['page-not-found']);
      }
      this.spinner.hide();
    });
    this.commentService.getAllTaskComments(projectId, taskId).subscribe((comments: Comment[]) => {
      if (this.task) {
        this.spinner.hide();
      }
      this.comments = comments;
    }, (e: HttpErrorResponse) => {
      console.log(e);
    });
  }

  uploadAttachments(attachments: File[]) {
    this.spinner.show();
    this.attachmentService.uploadAttachments(this.task.project.id, this.task.id, attachments).subscribe((data) => {
      console.log('server response', data);
      this.ngOnInit();
    }, (e: Error) => {
      console.log(e);
      this.spinner.hide();
    });
  }

  downloadAttachment(attachmentId: number) {
    this.attachmentService.downloadAttachment(this.task.project.id, this.task.id, attachmentId).subscribe(() => {
    }, (e: Error) => console.log(e));
  }

  submitComment(comment: Comment) {
    const taskId = this.route.snapshot.paramMap.get('taskId');
    const projectId = this.route.snapshot.paramMap.get('taskId');
    this.commentService.createComment(projectId, taskId, comment).subscribe(
      () => {
        this.commentService.getAllTaskComments(projectId, taskId).subscribe((comments: Comment[]) => {
          this.comments = comments;
        });
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
    this.spinner.show();
    this.taskService.updateTask(task).subscribe((data: Task) => {
      this.ngOnInit();
      this.isFormDisabled$.next(true);
    }, (e: Error) => {
      this.spinner.hide();
      console.log(e);
    });
  }

  onEditClick() {
    this.isFormDisabled$.next(!this.isFormDisabled$.value);
  }
}
