import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configs/validation-config';
import {hasError} from '../../util/has-error';
import {HasError} from '../../models/HasError';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css']
})
export class NewTaskComponent implements OnInit {

  newTaskForm: FormGroup;
  hasError;
  validationConfigs = validationConfigs;

  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder) {
    this.newTaskForm = this.formBuilder.group({
      projectCode: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      priority: '',
      dueDate: '',
      estimation: '',
      assignee: ''
    });
    this.hasError = hasError(this.newTaskForm);
  }

  ngOnInit() {
  }

  submitForm() {
    this.activeModal.close(this.newTaskForm.value);
  }
}
