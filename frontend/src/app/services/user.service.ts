import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {usersURI} from '../configs/conf';
import {User} from '../models/user';
import {Observable} from 'rxjs';
import {UserRole} from '../models/user-role';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(usersURI, user);
  }

  getAllUsersByRole(role: UserRole): Observable<User[]> {
    return this.http.get<User[]>(usersURI).pipe(
      map((v: User[]) => v.filter((u: User) => u.role.name === role))
    );
  }
}
