import {TaskPriority} from './task-priority';
import {TaskStatus} from './task-status';
import {User} from './user';
import {Project} from './project';

export class Task {
  id: number;
  project: Project;
  code: number;
  description: string;
  priority: { name: TaskPriority };
  status: TaskStatus;
  created: Date;
  dueDate: Date;
  updated: Date;
  estimation: number;
  logWork: number;
  assignee: User;
  reporter: User;
  comments: Comment[];

  constructor(project: Project, description: string, priority: TaskPriority, dueDate: Date, estimation: number, assignee: User) {
    this.project = project;
    this.description = description;
    this.priority = {name: priority};
    this.dueDate = dueDate;
    this.estimation = estimation;
    this.assignee = assignee;
  }
}
