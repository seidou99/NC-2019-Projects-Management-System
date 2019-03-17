import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css']
})
export class NewTaskComponent implements OnInit {

  newTaskForm: FormGroup;

  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder) {
    this.newTaskForm = this.formBuilder.group({
      projectCode: '',
      description: '',
      priority: '',
      dueDate: '',
      estimation: '',
      assignee: ''
    });
  }

  ngOnInit() {
  }

  submitForm() {
    this.activeModal.close(this.newTaskForm.value);
  }
}
