import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-home-navbar',
  templateUrl: './home-navbar.component.html',
  styleUrls: ['./home-navbar.component.css']
})
export class HomeNavbarComponent implements OnInit {

  constructor() {
  }

  @Input() isNewProjectAvailable: boolean;
  @Input() isNewUserAvailable: boolean;
  @Input() isNewTaskAvailable: boolean;

  @Output() createProjectClick = new EventEmitter();
  @Output() createUserClick = new EventEmitter();
  @Output() createTaskClick = new EventEmitter();
  @Output() logoutClick = new EventEmitter();

  ngOnInit() {
  }

}
