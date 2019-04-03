import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Task} from '../../models/task';
import {TaskPriority} from '../../models/task-priority';
import {TaskStatus} from '../../models/task-status';

@Component({
  selector: 'app-tasks-table',
  templateUrl: './tasks-table.component.html',
  styleUrls: ['./tasks-table.component.css']
})
export class TasksTableComponent implements OnInit {

  @Input() tasks: Task[];
  @Output() taskRowClick = new EventEmitter<number>();

  constructor() {
  }

  ngOnInit() {

  }
}
