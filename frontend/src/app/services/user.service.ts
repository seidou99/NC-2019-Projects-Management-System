import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {usersURI} from '../configs/conf';
import {User} from '../models/user';
import {Observable} from 'rxjs';
import {UserRole} from '../models/user-role';
import {map} from 'rxjs/operators';
import {prepareEnumForServer, processEnumFromServer} from '../util/process-enum';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  createUser(user: User): Observable<User> {
    prepareEnumForServer(user, 'role', UserRole);
    return this.http.post<User>(usersURI, user).pipe(
      map(processEnumFromServer('role', UserRole))
    );
  }
}
