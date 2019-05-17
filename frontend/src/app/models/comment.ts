import {User} from './user';
import {Task} from './task';

export class Comment {
  id: number;
  author: User;
  text: string;
  task: Task;
  date: Date;

  constructor(text: string) {
    this.text = text;
  }
}
