import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Project} from '../../models/project';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  @Input() projects: Project[] = [];
  @Output() projectChangeEmitter = new EventEmitter<number>();
  @Input() selectedProjectId = -1;

  constructor() {
  }

  changeProject(id: number) {
    this.projectChangeEmitter.emit(id);
  }

  ngOnInit() {
  }

}
