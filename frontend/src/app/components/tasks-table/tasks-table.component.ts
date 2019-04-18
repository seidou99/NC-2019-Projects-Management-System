import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Task} from '../../models/task';
import {TaskPriority} from '../../models/task-priority';
import {TaskStatus} from '../../models/task-status';
import {Observable} from 'rxjs/internal/Observable';

@Component({
  selector: 'app-tasks-table',
  templateUrl: './tasks-table.component.html',
  styleUrls: ['./tasks-table.component.css']
})
export class TasksTableComponent implements OnInit, OnChanges {

  @Input() page$: Observable<Task[]>;
  @Input() pageNumber: number;
  @Input() pageSize: number;
  @Input() recordsAmount: number;
  @Output() pageChange = new EventEmitter<number>();
  @Output() taskRowClick = new EventEmitter<number>();

  lastPage = 0;
  firstRowNumber = 0;
  lastRowNumber = 0;
  buttons = [];

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.buttons = [];
    this.lastPage = Math.ceil(this.recordsAmount / this.pageSize);
    this.buttons.push({name: 'First', pageNumber: 1},
      {name: 'Previous', pageNumber: this.pageNumber - 1});
    for (let i = -2; i < 2; i++) {
      const pageNumber = this.pageNumber + i;
      if (pageNumber > 0 && pageNumber <= this.lastPage) {
        this.buttons.push({name: pageNumber, pageNumber});
      }
    }
    this.buttons.push({name: 'Next', pageNumber: this.pageNumber + 1},
      {name: 'Last', pageNumber: this.lastPage});
    if (this.recordsAmount > 0) {
      this.firstRowNumber = (this.pageNumber - 1) * this.pageSize + 1;
    } else {
      this.firstRowNumber = 0;
    }
    if (this.pageSize * this.pageNumber <= this.recordsAmount) {
      this.lastRowNumber = this.pageNumber * this.pageSize;
    } else {
      this.lastRowNumber = this.recordsAmount;
    }
  }
}
