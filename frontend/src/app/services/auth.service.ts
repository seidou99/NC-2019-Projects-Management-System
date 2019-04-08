import {Injectable} from '@angular/core';
import {User} from '../models/user';
import {UserRole} from '../models/user-role';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() {
  }

  getUser(): User {
    const user = new User('name', 'surname', UserRole.PROJECT_MANAGER, 'mrskypavlik@gmail.com', null);
    user.id = 1;
    return user;
  }
}
