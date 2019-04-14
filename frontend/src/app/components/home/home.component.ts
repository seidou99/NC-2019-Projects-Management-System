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
import {Subject} from 'rxjs';
import {Page} from '../../models/page';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  tasks: Task[] = [];
  projects: Project[] = [];
  currentProjectId: number;
  pageNumber = 1;
  pageSize = 20;
  recordsAmount = 0;

  constructor(private modalService: NgbModal, private userService: UserService, private projectService: ProjectService,
              private router: Router, private taskService: TaskService, private authService: AuthService) {
  }

  ngOnInit() {
    this.projectService.getAllProjects().subscribe((projects: Project[]) => {
      this.projects = projects;
      if (projects.length) {
        this.currentProjectId = projects[0].id;
        this.loadTasks();
      }
    }, (e: Error) => console.log(e));
  }

  loadProjects() {
    this.projectService.getAllProjects().subscribe((projects: Project[]) => {
      this.projects = projects;
    }, (e: Error) => console.log(e));
  }

  loadTasks() {
    this.taskService.getAllTasksByProjectId(this.currentProjectId, this.pageNumber, this.pageSize).subscribe((data: Page<Task>) => {
      this.tasks = data.content;
      this.recordsAmount = data.totalElements;
    }, (e: Error) => console.log(e));
  }

  openNewProjectModal() {
    const modalRef = this.modalService.open(NewProjectComponent);
    modalRef.result.then((project: Project) => {
      this.projectService.createProject(project).subscribe(
        () => {
          this.loadProjects();
        },
        (e) => {
          console.log(e);
        }
      );
    }).catch((e) => {
      console.log('rejected ' + e);
    });
  }

  onTaskRowClick(taskId: number) {
    this.router.navigate(['tasks', taskId]);
  }

  openNewUserModal() {
    const modalRef = this.modalService.open(NewUserComponent);
    modalRef.result.then((user: User) => {
      this.userService.createUser(user).subscribe(
        () => {
        },
        (e) => {
          console.log(e);
        }
      );
    }).catch((e) => {
      console.log('rejected ' + e);
    });
  }

  openNewTaskModal() {
    const modalRef = this.modalService.open(NewTaskComponent);
    modalRef.result.then((task: Task) => {
      this.taskService.createTask(task).subscribe(() => {
          this.loadTasks();
        },
        e => console.log(e));
    }).catch((e) => {
      console.log('rejected ' + e);
    });
  }

  onPageChange(page: number) {
    this.pageNumber = page;
    this.loadTasks();
  }

  projectChange(projectId: number) {
    this.currentProjectId = projectId;
    this.loadTasks();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }
}
