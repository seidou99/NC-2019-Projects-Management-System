import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TaskStatus} from '../../models/task-status';
import {AuthService} from "../../services/auth.service";
import {UserRole} from "../../models/user-role";

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
  role: UserRole;
  UserRole = UserRole;

  constructor(public authService: AuthService) {
  }

  ngOnInit() {
    this.role = this.authService.getUserRole();
  }

}
