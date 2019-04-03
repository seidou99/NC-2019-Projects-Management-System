import {TaskPriority} from './task-priority';
import {TaskStatus} from './task-status';
import {User} from './user';
import {Project} from './project';

export class Task {
  id: number;
  code: string;
  priority: TaskPriority;
  status: TaskStatus;
  created: Date;
  updated: Date;
  dueDate: Date;
  estimation: number;
  logWork: number;
  description: string;
  assignee: User;
  project: Project;
}
