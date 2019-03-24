import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {TaskPriority} from '../../models/TaskPriority';
import {TaskStatus} from '../../models/TaskStatus';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-task-dashboard',
  templateUrl: './task-dashboard.component.html',
  styleUrls: ['./task-dashboard.component.css']
})
export class TaskDashboardComponent implements OnInit {

  commentForm: FormGroup;
  taskForm: FormGroup;
  availableStatus = [];
  availablePriority = [];
  isFormDisabled = true;

  comments = [
    {
      name: 'Name',
      surname: 'Surname',
      text: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab accusamus earum eius enim eos exercitationem fugit harum itaque laudantium natus necessitatibus non porro quae quaerat, quisquam reprehenderit sit sunt vitae?'
    },
    {
      name: 'Name',
      surname: 'Surname',
      text: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab accusamus earum eius enim eos exercitationem fugit harum itaque laudantium natus necessitatibus non porro quae quaerat, quisquam reprehenderit sit sunt vitae?'
    },
    {
      name: 'Name',
      surname: 'Surname',
      text: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab accusamus earum eius enim eos exercitationem fugit harum itaque laudantium natus necessitatibus non porro quae quaerat, quisquam reprehenderit sit sunt vitae?'
    },
  ];

  currentDate() {
    const currentDate = new Date();
    return currentDate.toISOString().substring(0, 10);
  }

  constructor(private formBuilder: FormBuilder) {
    this.commentForm = this.formBuilder.group({
      text: ''
    });
    this.taskForm = this.formBuilder.group({
      priority: new FormControl({value: '', disabled: this.isFormDisabled}),
      status: new FormControl({value: '', disabled: this.isFormDisabled}),
      assignee: new FormControl({value: '', disabled: this.isFormDisabled}),
      reporter: new FormControl({value: '', disabled: this.isFormDisabled}),
      estimation: new FormControl({value: this.currentDate(), disabled: this.isFormDisabled}),
      description: new FormControl({value: '', disabled: true}),
      created: new FormControl({value: this.currentDate(), disabled: this.isFormDisabled}, []),
      updated: new FormControl({value: this.currentDate(), disabled: this.isFormDisabled}),
      resolved: new FormControl({value: this.currentDate(), disabled: this.isFormDisabled}),
      closed: new FormControl({value: this.currentDate(), disabled: this.isFormDisabled})
    });
    for (const status in TaskStatus) {
      this.availableStatus.push(TaskStatus[status]);
    }
    for (const priority in TaskPriority) {
      this.availablePriority.push(TaskPriority[priority]);
    }
  }

  ngOnInit() {
  }

  submitComment() {
    console.log(this.commentForm.value);
  }

  submitTask() {
    console.log(this.taskForm.value);
  }

  enableEdit() {
    if(this.isFormDisabled) {
      this.isFormDisabled = false;
      for (const controlName in this.taskForm.controls) {
        this.taskForm.get(controlName).enable();
      }
    } else {
      this.isFormDisabled = true;
      for (const controlName in this.taskForm.controls) {
        this.taskForm.get(controlName).disable();
      }
    }

  }

}
