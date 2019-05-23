import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskService} from '../../services/task.service';
import {Task} from '../../models/task';
import {BehaviorSubject, Subject, Subscription} from 'rxjs';
import {TaskStatus} from '../../models/task-status';
import {CommentService} from '../../services/comment.service';
import {Comment} from '../../models/comment';
import {AttachmentService} from '../../services/attachment.service';
import {Ng4LoadingSpinnerService} from "ng4-loading-spinner";
import {HttpErrorResponse} from "@angular/common/http";
import {Alert} from "../../util/alert";

@Component({
  selector: 'app-task-dashboard',
  templateUrl: './task-dashboard.component.html',
  styleUrls: ['./task-dashboard.component.css']
})
export class TaskDashboardComponent implements OnInit, OnDestroy {

  taskName = '';
  task: Task;
  comments: Comment[] = [];
  task$ = new Subject<Task>();
  isFormDisabled$ = new BehaviorSubject<boolean>(true);
  alert: Alert;
  subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute, private taskService: TaskService, private commentService: CommentService,
              private attachmentService: AttachmentService, private spinner: Ng4LoadingSpinnerService, private router: Router) {
    this.alert = new Alert();
  }

  ngOnInit() {
    const taskId = this.route.snapshot.paramMap.get('taskId');
    const projectId = this.route.snapshot.paramMap.get('taskId');
    this.spinner.show();
    const sub1 = this.taskService.getTask(projectId, taskId).subscribe((task: Task) => {
      this.task = task;
      this.task$.next(task);
      this.taskName = `${task.project.code}-${task.code}`;
      this.spinner.hide();
    }, (e: HttpErrorResponse) => {
      console.log(e);
      if (e.status === 404) {
        this.router.navigate(['page-not-found']);
      } else {
        this.alert.showHttpError(e);
      }
      this.spinner.hide();
    });
    const sub2 = this.commentService.getAllTaskComments(projectId, taskId).subscribe((comments: Comment[]) => {
      if (this.task) {
        this.spinner.hide();
      }
      this.comments = comments;
    }, (e: HttpErrorResponse) => {
      this.alert.showHttpError(e);
    });
    this.subscriptions.push(sub1, sub2);
  }

  uploadAttachments(attachments: File[]) {
    this.spinner.show();
    const sub = this.attachmentService.uploadAttachments(this.task.project.id, this.task.id, attachments).subscribe((data) => {
      this.alert.showSuccess('Attachment uploaded');
      this.ngOnInit();
    }, (e: HttpErrorResponse) => {
      this.alert.showHttpError(e);
      this.spinner.hide();
    });
    this.subscriptions.push(sub);
  }

  downloadAttachment(attachmentId: number) {
    this.attachmentService.downloadAttachment(this.task.project.id, this.task.id, attachmentId).subscribe(() => {
    }, (e: HttpErrorResponse) => this.alert.showHttpError(e));
  }

  submitComment(comment: Comment) {
    const taskId = this.route.snapshot.paramMap.get('taskId');
    const projectId = this.route.snapshot.paramMap.get('taskId');
    const sub1 = this.commentService.createComment(projectId, taskId, comment).subscribe(
      () => {
        const sub2 = this.commentService.getAllTaskComments(projectId, taskId).subscribe((comments: Comment[]) => {
          this.comments = comments;
        });
        this.subscriptions.push(sub2);
      }, (e: HttpErrorResponse) => this.alert.showHttpError(e));
    this.subscriptions.push(sub1);
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
    const sub = this.taskService.updateTask(task).subscribe((data: Task) => {
      this.ngOnInit();
      this.isFormDisabled$.next(true);
      this.alert.showSuccess('Task updated');
    }, (e: HttpErrorResponse) => {
      this.spinner.hide();
      this.alert.showHttpError(e);
    });
    this.subscriptions.push(sub);
  }

  onEditClick() {
    this.isFormDisabled$.next(!this.isFormDisabled$.value);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }
}
