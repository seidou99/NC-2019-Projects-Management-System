import {User} from './user';

export class Comment {
  author: User;
  text: string;


  constructor(text: string) {
    this.text = text;
  }
}
