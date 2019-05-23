import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Observable, Subscription} from 'rxjs';
import {TaskSort} from '../../models/task-sort';
import {TaskOrder} from '../../models/task-order';
import {TaskSortAndOrder} from "../../models/task-sort-and-order";

@Component({
  selector: 'app-task-sort',
  templateUrl: './task-sort.component.html',
  styleUrls: ['./task-sort.component.css']
})
export class TaskSortComponent implements OnInit, OnDestroy {

  sortForm: FormGroup;
  @Input() initialState$: Observable<TaskSortAndOrder>;
  @Output() stateChange = new EventEmitter<TaskSortAndOrder>();
  availableSorts: TaskSort[] = [];
  availableOrders: TaskOrder[] = [];
  subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder) {
    this.sortForm = this.fb.group({
      sort: {},
      order: {}
    });
    for (const sort in TaskSort) {
      this.availableSorts.push(TaskSort[sort] as TaskSort);
    }
    for (const order in TaskOrder) {
      this.availableOrders.push(TaskOrder[order] as TaskOrder);
    }
  }

  ngOnInit() {
    const sub1 = this.initialState$.subscribe((v: TaskSortAndOrder) => {
      this.sortForm.patchValue({sort: v.sort, order: v.order});
    });
    const sub2 = this.sortForm.valueChanges.subscribe(() => {
      this.stateChange.emit(this.sortForm.getRawValue());
    });
    this.subscriptions.push(sub1, sub2);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }
}
