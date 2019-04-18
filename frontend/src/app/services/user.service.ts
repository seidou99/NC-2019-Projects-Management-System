import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {usersURI} from '../configs/conf';
import {User} from '../models/user';
import {Observable} from 'rxjs';
import {UserRole} from '../models/user-role';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  createUser(user: User): Observable<any> {
    return this.http.post(usersURI, user);
  }

  getAllUsersByRole(roles: UserRole[]): Observable<User[]> {
    const params = new HttpParams({fromObject: {roles}});
    return this.http.get<User[]>(usersURI, {params});
  }
}
