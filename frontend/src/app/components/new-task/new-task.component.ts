import {Component, OnInit, ViewChild} from '@angular/core';
import {NgbActiveModal, NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configs/conf';
import {createHasError, HasErrorFunction} from '../../util/has-error';
import {Project} from '../../models/project';
import {ProjectService} from '../../services/project.service';
import {merge, Observable, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';
import {UserRole} from '../../models/user-role';
import {TaskPriority} from '../../models/task-priority';
import {User} from '../../models/user';
import {Task} from '../../models/task';

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
  developers: User[] = [new User('biba', 's', UserRole.DEVELOPER, 'biba@gmail.com', '123'),
    new User('boba', 's', UserRole.DEVELOPER, 'boba@gmail.com', '123')];


  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder, private projectService: ProjectService) {
    for (const priority in TaskPriority) {
      this.taskPriorities.push(TaskPriority[priority]);
    }
    this.newTaskForm = this.formBuilder.group({
      code: new FormControl('', {
        validators: [Validators.required, this.valueContainsValidator(this.projects, this.projectMapper)], updateOn: 'blur'
      }),
      description: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.taskDescription.minlength),
        Validators.maxLength(validationConfigs.taskDescription.maxlength)]),
      priority: new FormControl(this.taskPriorities[0], [Validators.required]),
      dueDate: new FormControl('', [Validators.required]),
      estimation: new FormControl(validationConfigs.estimation.min, [Validators.required,
        Validators.min(validationConfigs.estimation.min)]),
      assignee: new FormControl('', {
        validators: [this.valueContainsValidator(this.developers, this.developerMapper)],
        updateOn: 'blur'
      })
    });
    this.hasError = createHasError(this.newTaskForm);
  }

  ngOnInit() {
    this.projectService.getAllProjects().subscribe(
      (data: Project[]) => {
        data.forEach((value: Project) => this.projects.push(value));
        // console.log('projects ' + this.projects);
      }, (e) => {
        console.log(e);
      }
    );
    this.projectCodeSearch = this.search<Project>(this.codeTypeahead, this.codeFocus$, this.codeClick$, this.projects, this.projectMapper);
    this.assigneeSearch = this.search<User>(this.assigneeTypeahead, this.assigneeFocus$, this.assigneeClick$,
      this.developers, this.developerMapper);
  }

  valueContainsValidator<T>(data: T[], mapper: (obj: T) => string) {
    return (control: FormControl) => {
      if (data.map(mapper).find((v: string) => v === control.value) || control.value === '') {
        return null;
      } else {
        return {valueContains: true};
      }
    };
  }

  projectMapper(obj: Project): string {
    return obj.code;
  }

  developerMapper(obj: User): string {
    return obj.surname + ' ' + obj.name + ' (' + obj.authData.email + ')';
  }

  search<T>(instance: NgbTypeahead, focus$: Subject<string>, click$: Subject<string>, rawData: T[], mapper: (obj: T) => string) {
    return (text$: Observable<string>) => {
      const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
      const clickWithClosedPopup$ = click$.pipe(
        filter(() => {
          return !instance.isPopupOpen();
        })
      );
      return merge(debouncedText$, focus$, clickWithClosedPopup$).pipe(
        map((term: string) => {
            let data = [];
            if (term === '') {
              data = rawData.map(mapper);
            } else {
              data = rawData.map(mapper).filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 20);
            }
            return data;
          }
        )
      );
    };
  }

  // Object
  // assignee: "s boba (boba@gmail.com)"
  // code: "1488"
  // description: "быоовидыврадфырва"
  // dueDate: "2019-04-12"
  // estimation: 1
  // priority: "Blocker"
  // __proto__: Object

  submitForm() {
    const formValue = this.newTaskForm.getRawValue();
    const project: Project = this.projects.filter((v: Project) => v.code === formValue.code)[0];
    let assignee: User = null;
    if (formValue.assignee.length) {
      assignee = this.developers.filter((v: User) => this.developerMapper(v) === formValue.assignee)[0];
    }
    const task = new Task(project, formValue.description, formValue.priority, new Date(formValue.dueDate), formValue.estimation, assignee);
    this.activeModal.close(task);
  }
}
