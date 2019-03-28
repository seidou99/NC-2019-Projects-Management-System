import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NewProjectComponent} from '../new-project/new-project.component';
import {NewUserComponent} from '../new-user/new-user.component';
import {NewTaskComponent} from '../new-task/new-task.component';
import {User} from '../../models/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private modalService: NgbModal) {
  }

  ngOnInit() {
  }

  openNewProjectModal() {
    const modalRef = this.modalService.open(NewProjectComponent, {centered: true});
    modalRef.result.then((result) => {
      console.log(result);
    }).catch((error) => {
      console.log('rejected ' + error);
    });
  }

  openNewUserModal() {
    const modalRef = this.modalService.open(NewUserComponent);
    modalRef.result.then((result: User) => {
      console.log(result);
    }).catch((error) => {
      console.log('rejected ' + error);
    });
  }

  openNewTaskModal() {
    const modalRef = this.modalService.open(NewTaskComponent);
    modalRef.result.then((result) => {
      console.log(result);
    }).catch((error) => {
      console.log('rejected ' + error);
    });
  }

}
