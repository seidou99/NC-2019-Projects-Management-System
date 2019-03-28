import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configs/conf';
import {createHasError, HasErrorFunction} from '../../util/has-error';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css']
})
export class NewTaskComponent implements OnInit {

  newTaskForm: FormGroup;
  hasError: HasErrorFunction;
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
    this.hasError = createHasError(this.newTaskForm);
  }

  ngOnInit() {
  }

  submitForm() {
    this.activeModal.close(this.newTaskForm.value);
  }
}
