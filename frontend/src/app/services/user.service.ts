import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {registerURI, usersURI} from '../configs/conf';
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

  createUser(user: User): Observable<any> {
    return this.http.post(registerURI, user);
  }

  getAllUsersByRole(roles: UserRole[]): Observable<User[]> {
    return this.http.get<User[]>(usersURI).pipe(
      map((v: User[]) => v.filter((u: User) => {
        return roles.find((role: UserRole) => u.role.name === role);
      }))
    );
  }
}
