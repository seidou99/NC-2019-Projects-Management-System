import {UserRole} from './user-role';

export class User {
  id: number;
  name: string;
  surname: string;
  role: { name: UserRole };
  authData: UserAuthData;

  constructor(name: string, surname: string, role: UserRole, email: string, password: string) {
    this.name = name;
    this.surname = surname;
    this.role = {name: role};
    this.authData = new UserAuthData(email, password);
  }
}

export class UserAuthData {
  id: number;
  email: string;
  password: string;

  constructor(email: string, password: string) {
    this.email = email;
    this.password = password;
  }
}
