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

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  tasks: Task[] = [];

  constructor(private modalService: NgbModal, private userService: UserService, private projectService: ProjectService,
              private router: Router, private taskService: TaskService) {
  }

  ngOnInit() {
    this.taskService.getAllTasks().subscribe((data) => this.tasks = data, (e) => console.log(e));
  }

  openNewProjectModal() {
    const modalRef = this.modalService.open(NewProjectComponent);
    modalRef.result.then((project: Project) => {
      this.projectService.createProject(project).subscribe(
        (data: Project) => {
          console.log('returned from server ' + JSON.stringify(data));
        },
        (e) => {
          console.log(e);
        }
      );
      // console.log(project);
    }).catch((e) => {
      console.log('rejected ' + e);
    });
  }

  onTaskRowClick(taskId: number) {
    console.log(taskId);
    this.router.navigate(['tasks', taskId]);
  }

  openNewUserModal() {
    const modalRef = this.modalService.open(NewUserComponent);
    modalRef.result.then((user: User) => {
      // console.log(user);
      this.userService.createUser(user).subscribe(
        (data: User) => {
          console.log('returned from server ' + JSON.stringify(data));
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
      console.log(task);
      this.taskService.createTask(task).subscribe((data) => {
          console.log(data);
          this.taskService.getAllTasks().subscribe((tasks: Task[]) => this.tasks = tasks);
        },
        e => console.log(e));
    }).catch((e) => {
      console.log('rejected ' + e);
    });
  }

}
