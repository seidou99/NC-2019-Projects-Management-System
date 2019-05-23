import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {usersURI} from '../configuration/config';
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
    let params = new HttpParams();
    roles.forEach((role: UserRole) => params = params.append('roles', '' + role));
    return this.http.get<User[]>(usersURI, {params});
  }

  // isEmailTaken(email: string): Observable<boolean> {
  //   const params = new HttpParams().set('email', email);
  //
  // }

}
