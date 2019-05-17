import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NewProjectComponent} from '../new-project/new-project.component';
import {NewUserComponent} from '../new-user/new-user.component';
import {NewTaskComponent} from '../new-task/new-task.component';
import {User} from '../../models/user';
import {UserService} from '../../services/user.service';
import {Project} from '../../models/project';
import {ProjectService} from '../../services/project.service';
import {Router} from '@angular/router';
import {Task} from '../../models/task';
import {TaskService} from '../../services/task.service';
import {Page} from '../../models/page';
import {AuthService} from '../../services/auth.service';
import {UserRole} from '../../models/user-role';
import {BehaviorSubject} from 'rxjs/internal/BehaviorSubject';
import {TaskType} from '../../models/task-type';
import {TaskSort} from '../../models/task-sort';
import {TaskOrder} from '../../models/task-order';
import {TaskSortAndOrder} from '../../models/task-sort-and-order';
import {HttpErrorResponse} from '@angular/common/http';
import {Ng4LoadingSpinnerService} from "ng4-loading-spinner";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  page$ = new BehaviorSubject<Task[]>([]);
  taskType = TaskType.ALL;
  taskSort: TaskSortAndOrder = {sort: TaskSort.CODE, order: TaskOrder.ASC};
  initialTaskTypes$ = new BehaviorSubject<TaskType>(this.taskType);
  initialTaskSort$ = new BehaviorSubject<TaskSortAndOrder>(this.taskSort);
  projects: Project[] = [];
  currentProjectId: number;
  pageNumber = 1;
  pageSize = 10;
  recordsAmount = 0;
  UserRole = UserRole;
  alert = {
    type: '',
    message: '',
    timer: 0,
    showTime: 10000
  };

  constructor(private modalService: NgbModal, private userService: UserService, private projectService: ProjectService,
              private router: Router, private taskService: TaskService, public authService: AuthService,
              private spinner: Ng4LoadingSpinnerService) {
  }

  ngOnInit() {
    if (this.authService.getUserRole() === UserRole.ADMIN) {
      return;
    }
    this.spinner.show();
    this.projectService.getAllProjects(this.taskType).subscribe((projects: Project[]) => {
      this.projects = projects;
      if (projects.length) {
        this.currentProjectId = projects[0].id;
        this.loadTasks();
      } else {
        this.spinner.hide();
      }
    }, (e: Error) => {
      console.log(e);
      this.spinner.hide();
    });
  }

  taskTypesChange(taskType: TaskType) {
    this.taskType = taskType;
    this.pageNumber = 1;
    this.spinner.show();
    this.loadProjects(() => {
      if (this.projects.length) {
        this.currentProjectId = this.projects[0].id;
      }
      this.loadTasks();
    });
  }

  searchTask(taskName: string) {
    this.taskService.findTaskByName(taskName).subscribe((t: Task) => {
      this.router.navigate(['projects', t.project.id, 'tasks', t.id]);
    }, (e: HttpErrorResponse) => {
      if (e.status === 404) {
        this.showAlert(`Task with name '${taskName}' not found`, 'danger');
      }
    });
  }

  taskSortChange(taskSort: TaskSortAndOrder) {
    this.taskSort = taskSort;
    this.spinner.show();
    this.loadTasks();
  }

  loadProjects(afterLoadCallback?: any) {
    this.spinner.show();
    this.projectService.getAllProjects(this.taskType).subscribe((projects: Project[]) => {
      this.projects = projects;
      if (afterLoadCallback) {
        afterLoadCallback();
      }
      this.spinner.hide();
    }, (e: Error) => {
      console.log(e);
      this.spinner.hide()
    });
  }

  loadTasks() {
    this.spinner.show();
    this.taskService.getTasksPage(`${this.currentProjectId}`, this.pageNumber, this.pageSize, this.taskType, this.taskSort)
      .subscribe((data: Page<Task>) => {
        this.page$.next(data.content);
        this.recordsAmount = data.totalElements;
        this.spinner.hide();
      }, (e: Error) => {
        this.spinner.hide();
        console.log(e);
      });
  }

  openNewProjectModal() {
    const modalRef = this.modalService.open(NewProjectComponent);
    modalRef.result.then((project: Project) => {
      this.spinner.show();
      this.projectService.createProject(project).subscribe(
        () => {
          this.loadProjects(null);
          this.showAlert('Project created', 'success');
        },
        (e: HttpErrorResponse) => {
          console.log(e);
          this.spinner.hide();
          if (e.error && e.error.message) {
            this.showAlert((e.error.message as string).replace(e.status + ' ', ''), 'danger');
          }
        }
      );
    }).catch((e) => {
      console.log('rejected ' + e);
    });
  }

  onTaskRowClick(taskId: number) {
    this.router.navigate(['projects', this.currentProjectId, 'tasks', taskId]);
  }

  private showAlert(message: string, type: string) {
    clearTimeout(this.alert.timer);
    this.alert.message = message;
    this.alert.type = type;
    this.alert.timer = setTimeout(() => {
      this.alert.message = '';
    }, this.alert.showTime);
  }

  openNewUserModal() {
    const modalRef = this.modalService.open(NewUserComponent);
    modalRef.result.then((user: User) => {
      this.spinner.show();
      this.userService.createUser(user).subscribe(
        () => {
          this.spinner.hide();
          this.showAlert(`User with email ${user.authData.email} created`, 'success');
        },
        (e: HttpErrorResponse) => {
          this.spinner.hide();
          if (e.error && e.error.message) {
            this.showAlert((e.error.message as string).replace(e.status + ' ', ''), 'danger');
          }
          console.log(e);
        }
      );
    }).catch((e) => {
      console.log('rejected ', e);
    });
  }

  openNewTaskModal() {
    const modalRef = this.modalService.open(NewTaskComponent);
    modalRef.result.then((task: Task) => {
      this.spinner.show();
      this.taskService.createTask(task).subscribe(() => {
          this.loadTasks();
          this.showAlert(`Task created`, 'success');
        },
        (e: HttpErrorResponse) => {
          if (e.error && e.error.message) {
            this.showAlert((e.error.message as string).replace(e.status + ' ', ''), 'danger');
          }
        });
    }).catch((e) => {
      console.log('rejected ' + e);
    });
  }

  onPageChange(page: number) {
    this.pageNumber = page;
    this.spinner.show();
    this.loadTasks();
  }

  projectChange(projectId: number) {
    this.currentProjectId = projectId;
    this.spinner.show();
    this.loadTasks();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

  isNewProjectAvailable(): boolean {
    return this.authService.getUserRole() === UserRole.PROJECT_MANAGER;
  }

  isNewUserAvailable(): boolean {
    return this.authService.getUserRole() === UserRole.ADMIN;
  }

  isNewTaskAvailable(): boolean {
    const currentRole = this.authService.getUserRole();
    const reporterRoles = [UserRole.PROJECT_MANAGER, UserRole.DEVELOPER, UserRole.QA];
    return reporterRoles.findIndex((v: UserRole) => v === currentRole) > -1;
  }
}
