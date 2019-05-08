import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Observable} from "rxjs/internal/Observable";
import {TaskType} from "../../models/task-type";

@Component({
  selector: 'app-change-task-type',
  templateUrl: './change-task-type.component.html',
  styleUrls: ['./change-task-type.component.css']
})
export class ChangeTaskTypeComponent implements OnInit {

  @Input() initialState$: Observable<string>;
  @Output() stateChange = new EventEmitter<TaskType>();
  radioGroupForm: FormGroup;
  taskTypes = [];

  constructor(private fb: FormBuilder) {
    this.radioGroupForm = this.fb.group({
      state: ''
    });
    for (let type in TaskType) {
      this.taskTypes.push(TaskType[type]);
    }
  }

  ngOnInit() {
    this.initialState$.subscribe((state: string) => {
      this.radioGroupForm.patchValue({state});
    }, (error: Error) => console.log(error));
    this.radioGroupForm.valueChanges.subscribe((formData: { state: TaskType }) => {
      this.stateChange.emit(formData.state);
    }, (error: Error) => console.log(error));
  }

}
