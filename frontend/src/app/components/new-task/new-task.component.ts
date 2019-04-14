import {Component, OnInit, ViewChild} from '@angular/core';
import {NgbActiveModal, NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configs/conf';
import {createHasError, HasErrorFunction} from '../../util/has-error';
import {Project} from '../../models/project';
import {ProjectService} from '../../services/project.service';
import {Subject} from 'rxjs';
import {UserRole} from '../../models/user-role';
import {TaskPriority} from '../../models/task-priority';
import {User} from '../../models/user';
import {Task} from '../../models/task';
import {UserService} from '../../services/user.service';
import {DatePipe} from '@angular/common';
import {getNextDay, minDateValidator, transformDate} from '../../util/date';
import {projectMapper, search, userMapper, valueContainsValidator} from '../../util/typeahead';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css']
})
export class NewTaskComponent implements OnInit {

  newTaskForm: FormGroup;
  hasError: HasErrorFunction;
  validationConfigs = validationConfigs;
  projects: Project[] = [];
  @ViewChild('codeTypeahead') codeTypeahead: NgbTypeahead;
  @ViewChild('assigneeTypeahead') assigneeTypeahead: NgbTypeahead;
  codeFocus$ = new Subject<string>();
  codeClick$ = new Subject<string>();
  assigneeFocus$ = new Subject<string>();
  assigneeClick$ = new Subject<string>();
  taskPriorities: string[] = [];
  projectCodeSearch;
  assigneeSearch;
  developers: User[] = [];
  minDueDate: string = transformDate(getNextDay());

  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder, private projectService: ProjectService,
              private userService: UserService, private datePipe: DatePipe) {
    for (const priority in TaskPriority) {
      this.taskPriorities.push(TaskPriority[priority]);
    }
    this.newTaskForm = this.formBuilder.group({
      code: new FormControl('', {
        validators: [Validators.required, valueContainsValidator(this.projects, projectMapper)], updateOn: 'blur'
      }),
      description: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.taskDescription.minlength),
        Validators.maxLength(validationConfigs.taskDescription.maxlength)]),
      priority: new FormControl(this.taskPriorities[0], [Validators.required]),
      dueDate: new FormControl(this.minDueDate, [Validators.required, minDateValidator(getNextDay())]),
      estimation: new FormControl(validationConfigs.estimation.min, [Validators.required,
        Validators.min(validationConfigs.estimation.min)]),
      assignee: new FormControl('', {
        validators: [valueContainsValidator(this.developers, userMapper)],
        updateOn: 'blur'
      })
    });
    this.hasError = createHasError(this.newTaskForm);
  }

  ngOnInit() {
    this.projectService.getAllProjects().subscribe(
      (data: Project[]) => {
        data.forEach((value: Project) => this.projects.push(value));
      }, (e) => {
        console.log(e);
      }
    );
    this.userService.getAllUsersByRole([UserRole.DEVELOPER, UserRole.QA]).subscribe((data: User[]) => {
      data.forEach((u: User) => this.developers.push(u));
    }, (e) => console.log(e));
    this.projectCodeSearch = search<Project>(this.codeTypeahead, this.codeFocus$, this.codeClick$, this.projects, projectMapper);
    this.assigneeSearch = search<User>(this.assigneeTypeahead, this.assigneeFocus$, this.assigneeClick$,
      this.developers, userMapper);
  }

  submitForm() {
    const formValue = this.newTaskForm.getRawValue();
    const project: Project = this.projects.filter((v: Project) => v.code === formValue.code)[0];
    let assignee: User = null;
    if (formValue.assignee.length) {
      assignee = this.developers.filter((v: User) => userMapper(v) === formValue.assignee)[0];
    }
    const task = new Task(project, formValue.description, formValue.priority, new Date(formValue.dueDate), formValue.estimation, assignee);
    this.activeModal.close(task);
  }
}
