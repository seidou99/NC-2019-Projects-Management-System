import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TaskService} from '../../services/task.service';
import {Task} from '../../models/task';
import {BehaviorSubject, Subject} from 'rxjs';
import {TaskStatus} from '../../models/task-status';
import {CommentService} from '../../services/comment.service';
import {Comment} from '../../models/comment';
import {AuthService} from '../../services/auth.service';
import {AttachmentService} from "../../services/attachment.service";

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
              private attachmentService: AttachmentService) {
  }

  ngOnInit() {
    const taskId = this.route.snapshot.paramMap.get('taskId');
    const projectId = this.route.snapshot.paramMap.get('taskId');
    this.taskService.getTask(projectId, taskId).subscribe((task: Task) => {
      this.task = task;
      this.task$.next(task);
      this.taskName = `${task.project.code}-${task.code}`;
    }, (e: Error) => console.log(e));
    this.commentService.getAllTaskComments(projectId, taskId).subscribe((comments: Comment[]) => this.comments = comments,
      (e: Error) => console.log(e));
  }

  uploadAttachments(attachments: File[]) {
    this.attachmentService.uploadAttachments(this.task.project.id, this.task.id, attachments).subscribe((data) => {
      console.log('server response', data);
      this.ngOnInit();
    }, (e: Error) => console.log(e));
  }

  downloadAttachment(attachmentId: number) {
    this.attachmentService.downloadAttachment(this.task.project.id, this.task.id, attachmentId);
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
    this.taskService.updateTask(task).subscribe((data: Task) => {
      this.ngOnInit();
      this.isFormDisabled$.next(true);
    }, (e: Error) => console.log(e));
  }

  onEditClick() {
    this.isFormDisabled$.next(!this.isFormDisabled$.value);
  }
}
