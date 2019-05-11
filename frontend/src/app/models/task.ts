import {TaskPriority} from './task-priority';
import {TaskStatus} from './task-status';
import {User} from './user';
import {Project} from './project';
import {Attachment} from "./attachment";

export class Task {
  id: number;
  project: Project;
  code: number;
  description: string;
  attachments: Attachment[];
  priority: {
    name: TaskPriority,
    id: number
  };
  status: {
    name: TaskStatus,
    id: number
  };
  created: Date;
  dueDate: Date;
  updated: Date;
  resolved: Date;
  closed: Date;
  estimation: number;
  assignee: User;
  reporter: User;

  constructor(project: Project, description: string, priority: TaskPriority, dueDate: Date, estimation: number, assignee: User) {
    this.project = project;
    this.description = description;
    this.priority = {name: priority, id: null};
    this.dueDate = dueDate;
    this.estimation = estimation;
    this.assignee = assignee;
  }
}
