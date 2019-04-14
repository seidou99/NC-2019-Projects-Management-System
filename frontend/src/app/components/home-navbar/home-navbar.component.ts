import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-home-navbar',
  templateUrl: './home-navbar.component.html',
  styleUrls: ['./home-navbar.component.css']
})
export class HomeNavbarComponent implements OnInit {

  constructor() {
  }

  @Output() createProjectClick = new EventEmitter();
  @Output() createUserClick = new EventEmitter();
  @Output() createTaskClick = new EventEmitter();
  @Output() logoutClick = new EventEmitter();

  ngOnInit() {
  }

}
