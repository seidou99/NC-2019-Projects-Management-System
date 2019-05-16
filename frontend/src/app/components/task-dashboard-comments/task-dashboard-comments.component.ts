import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Comment} from '../../models/comment';
import {validationConfigs} from '../../configs/conf';
import {createHasError, HasErrorFunction} from '../../util/has-error';

@Component({
  selector: 'app-task-dashboard-comments',
  templateUrl: './task-dashboard-comments.component.html',
  styleUrls: ['./task-dashboard-comments.component.css']
})
export class TaskDashboardCommentsComponent {

  @Output() submitComment = new EventEmitter<Comment>();
  @Input() comments: Comment[] = [];
  commentForm: FormGroup;
  hasError: HasErrorFunction;
  validationConfigs = validationConfigs;

  constructor(private formBuilder: FormBuilder) {
    this.commentForm = this.formBuilder.group({
      text: new FormControl('', [Validators.required, Validators.maxLength(validationConfigs.comment.maxlength)])
    });
    this.hasError = createHasError(this.commentForm);
  }

  onCommentSubmit() {
    const control = this.commentForm.get('text');
    const text = control.value;
    // control.patchValue('');
    control.reset('');
    this.submitComment.emit(new Comment(text));
  }
}
