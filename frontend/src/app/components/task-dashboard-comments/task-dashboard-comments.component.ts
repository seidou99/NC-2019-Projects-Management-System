import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Comment} from '../../models/comment';

@Component({
  selector: 'app-task-dashboard-comments',
  templateUrl: './task-dashboard-comments.component.html',
  styleUrls: ['./task-dashboard-comments.component.css']
})
export class TaskDashboardCommentsComponent implements OnInit {

  @Output() submitComment = new EventEmitter<Comment>();
  @Input() comments: Comment[] = [];
  commentForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.commentForm = this.formBuilder.group({
      text: ''
    });

    // const comment = new Comment()

    // {
    //   name: 'Name',
    //     surname: 'Surname',
    //   text: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab accusamus earum eius enim eos exercitationem fugit harum itaque laudantium natus necessitatibus non porro quae quaerat, quisquam reprehenderit sit sunt vitae?'
    // },
    // {
    //   name: 'Name',
    //     surname: 'Surname',
    //   text: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab accusamus earum eius enim eos exercitationem fugit harum itaque laudantium natus necessitatibus non porro quae quaerat, quisquam reprehenderit sit sunt vitae?'
    // },
    // {
    //   name: 'Name',
    //     surname: 'Surname',
    //   text: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab accusamus earum eius enim eos exercitationem fugit harum itaque laudantium natus necessitatibus non porro quae quaerat, quisquam reprehenderit sit sunt vitae?'
    // }
  }

  onCommentSubmit() {
    const text = this.commentForm.get('text').value;
    const comment = new Comment(text);
  }

  ngOnInit() {

  }

}
