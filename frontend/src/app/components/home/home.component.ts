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

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private modalService: NgbModal, private userService: UserService, private projectService: ProjectService, private router: Router) {
  }

  ngOnInit() {
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
    modalRef.result.then((result) => {
      console.log(result);
    }).catch((e) => {
      console.log('rejected ' + e);
    });
  }

}
