import {Component, OnDestroy, OnInit} from '@angular/core';
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
import {Alert} from "../../util/alert";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  page$ = new BehaviorSubject<Task[]>([]);
  taskType = TaskType.ALL;
  taskSort: TaskSortAndOrder = {sort: TaskSort.CODE, order: TaskOrder.ASC};
  initialTaskTypes$ = new BehaviorSubject<TaskType>(this.taskType);
  initialTaskSort$ = new BehaviorSubject<TaskSortAndOrder>(this.taskSort);
  projects: Project[] = [];
  currentProjectId: number;
  pageNumber = 1;
  pageSize = 6;
  recordsAmount = 0;
  UserRole = UserRole;
  alert: Alert;
  subscriptions: Subscription[] = [];

  constructor(private modalService: NgbModal, private userService: UserService, private projectService: ProjectService,
              private router: Router, private taskService: TaskService, public authService: AuthService,
              private spinner: Ng4LoadingSpinnerService) {
    this.alert = new Alert();
  }

  ngOnInit() {
    if (this.authService.getUserRole() === UserRole.ADMIN) {
      return;
    }
    this.spinner.show();
    const sub = this.projectService.getAllProjects(this.taskType).subscribe((projects: Project[]) => {
      this.projects = projects;
      if (projects.length) {
        this.currentProjectId = projects[0].id;
        this.loadTasks();
      } else {
        this.spinner.hide();
      }
    }, (e: HttpErrorResponse) => {
      this.alert.showHttpError(e);
      this.spinner.hide();
    });
    this.subscriptions.push(sub);
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
    const sub = this.taskService.findTaskByName(taskName).subscribe((t: Task) => {
      this.router.navigate(['projects', t.project.id, 'tasks', t.id]);
    }, (e: HttpErrorResponse) => {
      this.alert.showHttpError(e);
      this.spinner.hide();
    });
    this.subscriptions.push(sub);
  }

  taskSortChange(taskSort: TaskSortAndOrder) {
    this.taskSort = taskSort;
    this.spinner.show();
    this.loadTasks();
  }

  loadProjects(afterLoadCallback?: any) {
    this.spinner.show();
    const sub = this.projectService.getAllProjects(this.taskType).subscribe((projects: Project[]) => {
      this.projects = projects;
      if (afterLoadCallback) {
        afterLoadCallback();
      }
      this.spinner.hide();
    }, (e: HttpErrorResponse) => {
      this.alert.showHttpError(e);
      this.spinner.hide()
    });
    this.subscriptions.push(sub);
  }

  loadTasks() {
    this.spinner.show();
    const sub = this.taskService.getTasksPage(`${this.currentProjectId}`, this.pageNumber,
      this.pageSize, this.taskType, this.taskSort).subscribe((data: Page<Task>) => {
      this.page$.next(data.content);
      this.recordsAmount = data.totalElements;
      this.spinner.hide();
    }, (e: HttpErrorResponse) => {
      this.spinner.hide();
      this.alert.showHttpError(e);
    });
    this.subscriptions.push(sub);
  }

  openNewProjectModal() {
    const modalRef = this.modalService.open(NewProjectComponent);
    modalRef.result.then((project: Project) => {
      this.spinner.show();
      const sub = this.projectService.createProject(project).subscribe(
        () => {
          this.loadProjects(null);
          this.alert.showSuccess('Project created');
        },
        (e: HttpErrorResponse) => {
          this.spinner.hide();
          this.alert.showHttpError(e);
        }
      );
      this.subscriptions.push(sub);
    }).catch((e) => {
    });
  }

  onTaskRowClick(taskId: number) {
    this.router.navigate(['projects', this.currentProjectId, 'tasks', taskId]);
  }

  openNewUserModal() {
    const modalRef = this.modalService.open(NewUserComponent);
    modalRef.result.then((user: User) => {
      this.spinner.show();
      const sub = this.userService.createUser(user).subscribe(
        () => {
          this.spinner.hide();
          this.alert.showSuccess(`User with email ${user.authData.email} created`)
        },
        (e: HttpErrorResponse) => {
          this.spinner.hide();
          this.alert.showHttpError(e);
        }
      );
      this.subscriptions.push(sub);
    }).catch((e) => {
      console.log('rejected ', e);
    });
  }

  openNewTaskModal() {
    const modalRef = this.modalService.open(NewTaskComponent);
    modalRef.result.then((task: Task) => {
      this.spinner.show();
      const sub = this.taskService.createTask(task).subscribe(() => {
          this.loadTasks();
          this.alert.showSuccess(`Task created`);
        },
        (e: HttpErrorResponse) => {
          this.spinner.hide();
          this.alert.showHttpError(e);
        });
      this.subscriptions.push(sub);
    }).catch((e) => {
      }
    );
  }

  onPageChange(page: number) {
    this.pageNumber = page;
    this.spinner.show();
    this.loadTasks();
  }

  projectChange(projectId: number) {
    this.currentProjectId = projectId;
    this.spinner.show();
    this.pageNumber = 1;
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

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }
}
