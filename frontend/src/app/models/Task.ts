import {TaskPriority} from './TaskPriority';
import {TaskStatus} from './TaskStatus';

export class Task {
  projectId: number;
  taskId: number;
  taskCode: string;
  taskPriority: TaskPriority;
  taskStatus: TaskStatus;
  created: Date;
  updated: Date;
  dueDate: Date;
  estimation: Date;
  assignee: string;
  description: string;
}
