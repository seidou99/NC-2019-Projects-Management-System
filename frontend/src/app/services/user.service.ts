import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {usersURI} from '../configs/conf';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  createUser(user: User) {
    this.http.post(usersURI, user);
  }
}
