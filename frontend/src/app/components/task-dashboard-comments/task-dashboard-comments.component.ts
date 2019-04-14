import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Comment} from '../../models/comment';

@Component({
  selector: 'app-task-dashboard-comments',
  templateUrl: './task-dashboard-comments.component.html',
  styleUrls: ['./task-dashboard-comments.component.css']
})
export class TaskDashboardCommentsComponent {

  @Output() submitComment = new EventEmitter<Comment>();
  @Input() comments: Comment[] = [];
  commentForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.commentForm = this.formBuilder.group({
      text: ''
    });
  }

  onCommentSubmit() {
    const text = this.commentForm.get('text').value;
    this.submitComment.emit(new Comment(text));
  }
}
