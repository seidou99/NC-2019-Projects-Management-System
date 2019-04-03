import {Component, OnInit, ViewChild} from '@angular/core';
import {NgbActiveModal, NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validationConfigs} from '../../configs/conf';
import {createHasError, HasErrorFunction} from '../../util/has-error';
import {Project} from '../../models/project';
import {ProjectService} from '../../services/project.service';
import {from, merge, Observable, of, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';
import {fromArray} from 'rxjs/internal/observable/fromArray';
import {UserRole} from '../../models/user-role';
import {TaskPriority} from '../../models/task-priority';

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
  @ViewChild('instance') instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();
  codes: string[] = ['project1', 'project2'];
  taskPriorities: string[] = [];

  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder, private projectService: ProjectService) {
    for (const priority in TaskPriority) {
      this.taskPriorities.push(TaskPriority[priority]);
    }
    this.newTaskForm = this.formBuilder.group({
      code: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required, Validators.minLength(validationConfigs.taskDescription.minlength),
        Validators.maxLength(validationConfigs.taskDescription.maxlength)]),
      priority: new FormControl(this.taskPriorities[0], [Validators.required]),
      dueDate: new FormControl('', [Validators.required]),
      estimation: new FormControl('', [Validators.required]),
      assignee: new FormControl('', [Validators.required])
    });
    this.hasError = createHasError(this.newTaskForm);
  }

  ngOnInit() {
    this.projectService.getAllProjects().subscribe(
      (data: Project[]) => {
        this.projects = data;
        console.log(this.projects);
      }, (e) => {
        console.log(e);
      }
    );
  }

  search = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clickWithClosedPopup$ = this.click$.pipe(
      filter(() => {
        return !this.instance.isPopupOpen();
      })
    );
    const inputFocus$ = this.focus$;
    return merge(debouncedText$, inputFocus$, clickWithClosedPopup$).pipe(
      map((term) => {
          let data = [];
          if (term === '') {
            data = this.projects.map(project => project.code);
          } else {
            data = this.projects.map(project => project.code)
              .filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 20);
          }
          console.log(data);
          return data;
        }
      )
    );
  };

  submitForm() {
    this.activeModal.close(this.newTaskForm.value);
  }
}
