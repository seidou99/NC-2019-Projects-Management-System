import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TaskStatus} from '../../models/task-status';

@Component({
  selector: 'app-task-dashboard-navbar',
  templateUrl: './task-dashboard-navbar.component.html',
  styleUrls: ['./task-dashboard-navbar.component.css']
})
export class TaskDashboardNavbarComponent implements OnInit {

  @Output() editClick = new EventEmitter();
  @Output() assignClick = new EventEmitter();
  @Output() statusChange = new EventEmitter<TaskStatus>();
  @Input() header = '';
  TaskStatus = TaskStatus;

  constructor() {
  }

  ngOnInit() {

  }

}
