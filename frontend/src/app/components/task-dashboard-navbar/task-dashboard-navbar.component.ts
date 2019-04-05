import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-task-dashboard-navbar',
  templateUrl: './task-dashboard-navbar.component.html',
  styleUrls: ['./task-dashboard-navbar.component.css']
})
export class TaskDashboardNavbarComponent implements OnInit {

  @Output() editClick = new EventEmitter();
  @Output() assignClick = new EventEmitter();
  @Output() startClick = new EventEmitter();
  @Output() resolveClick = new EventEmitter();
  @Output() readyForTestClick = new EventEmitter();
  @Output() reOpenClick = new EventEmitter();
  @Output() closeClick = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

}
