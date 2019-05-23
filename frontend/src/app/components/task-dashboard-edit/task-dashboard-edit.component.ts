import {Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskStatus} from '../../models/task-status';
import {TaskPriority} from '../../models/task-priority';
import {Task} from '../../models/task';
import {minDateValidator} from '../../util/date';
import {Observable, Subject, Subscription} from 'rxjs';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {User} from '../../models/user';
import {createHasError, HasErrorFunction} from '../../util/has-error';
import {search, userMapper, valueContainsValidator} from '../../util/typeahead';
import {UserService} from '../../services/user.service';
import {UserRole} from '../../models/user-role';
import {validationConfigs} from '../../configuration/config';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-task-dashboard-edit',
  templateUrl: './task-dashboard-edit.component.html',
  styleUrls: ['./task-dashboard-edit.component.css']
})
export class TaskDashboardEditComponent implements OnInit, OnDestroy {

  @Input() task$: Observable<Task>;
  @Input() isFormDisabled$: Observable<boolean>;
  @Output() submitTask = new EventEmitter<Task>();
  @ViewChild('assigneeTypeahead') assigneeTypeahead: NgbTypeahead;
  assigneeFocus$ = new Subject<string>();
  assigneeClick$ = new Subject<string>();
  taskForm: FormGroup;
  availableStatus = [];
  availablePriority = [];
  assignees: User[] = [];
  assigneeSearch;
  hasError: HasErrorFunction;
  validationConfigs = validationConfigs;
  minDueDate: string;
  task: Task;
  subscriptions: Subscription[] = [];

  constructor(private formBuilder: FormBuilder, private userService: UserService, private datePipe: DatePipe) {
    this.minDueDate = datePipe.transform(new Date(), 'yyyy-MM-dd');
    this.taskForm = this.formBuilder.group({
      priority: new FormControl('', [Validators.required]),
      status: new FormControl('', [Validators.required]),
      assignee: new FormControl('', {
        validators: [
          Validators.required,
          valueContainsValidator(this.assignees, userMapper)
        ],
        updateOn: 'blur'
      }),
      reporter: new FormControl(''),
      estimation: new FormControl('', [Validators.required, Validators.min(validationConfigs.estimation.min)]),
      description: new FormControl('', [
        Validators.required,
        Validators.minLength(validationConfigs.taskDescription.minlength),
        Validators.maxLength(validationConfigs.taskDescription.maxlength)
      ]),
      created: new FormControl(''),
      dueDate: new FormControl('', [
        Validators.required,
        minDateValidator(new Date())
      ]),
      updated: new FormControl(''),
      resolved: new FormControl(''),
      closed: new FormControl('')
    });
    for (const status in TaskStatus) {
      this.availableStatus.push(TaskStatus[status]);
    }
    for (const priority in TaskPriority) {
      this.availablePriority.push(TaskPriority[priority]);
    }
    this.hasError = createHasError(this.taskForm);
  }

  ngOnInit(): void {
    this.subscribeToFormToggle();
    this.subscribeToFormInit();
    this.assigneeSearch = search<User>(this.assigneeTypeahead, this.assigneeFocus$, this.assigneeClick$, this.assignees, userMapper);
    this.loadData();
  }

  private loadData() {
    const sub = this.userService.getAllUsersByRole([UserRole.QA, UserRole.DEVELOPER, UserRole.PROJECT_MANAGER])
      .subscribe((assignees: User[]) => {
        this.assignees.splice(0, this.assignees.length);
        assignees.forEach((assignee: User) => this.assignees.push(assignee));
      }, (e: Error) => console.log(e));
    this.subscriptions.push(sub);
  }

  private subscribeToFormToggle() {
    const sub = this.isFormDisabled$.subscribe((isDisabled: boolean) => {
      if (isDisabled) {
        this.taskForm.disable();
      } else {
        const allowedToEdit = ['priority', 'status', 'description', 'assignee', 'estimation', 'dueDate'];
        allowedToEdit.forEach((v: string) => {
          const control = this.taskForm.get(v);
          control.enable();
          control.markAsDirty();
        });
      }
    }, (e: Error) => console.log(e));
    this.subscriptions.push(sub);
  }

  private subscribeToFormInit() {
    const sub = this.task$.subscribe((task: Task) => {
      this.task = task;
      const data = {
        priority: task.priority.name,
        status: task.status.name,
        assignee: userMapper(task.assignee),
        reporter: userMapper(task.reporter),
        estimation: task.estimation,
        description: task.description,
        created: task.created ? this.datePipe.transform(task.created, 'yyyy-MM-dd') : '',
        dueDate: task.dueDate ? this.datePipe.transform(task.dueDate, 'yyyy-MM-dd') : '',
        updated: task.updated ? this.datePipe.transform(task.dueDate, 'yyyy-MM-dd') : '',
        resolved: task.resolved ? this.datePipe.transform(task.resolved, 'yyyy-MM-dd') : '',
        closed: task.closed ? this.datePipe.transform(task.closed, 'yyyy-MM-dd') : ''
      };
      Object.keys(data).forEach((controlName: string) => {
        const control = this.taskForm.get(controlName);
        control.setValue(data[controlName]);
        control.updateValueAndValidity();
      });
    }, (e: Error) => console.log(e));
    this.subscriptions.push(sub);
  }

  submitForm() {
    const formValue = this.taskForm.getRawValue();
    this.task.assignee = this.assignees.filter((v: User) => userMapper(v) === formValue.assignee)[0];
    this.task.priority.name = formValue.priority;
    this.task.status.name = formValue.status;
    this.task.estimation = formValue.estimation;
    this.task.description = formValue.description;
    this.task.dueDate = formValue.dueDate;
    this.submitTask.emit(this.task);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }
}
