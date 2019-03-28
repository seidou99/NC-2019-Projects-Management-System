import {UserRole} from './user-role';

export class User {
  id: number;
  name: string;
  surname: string;
  email: string;
  password: string;
  role: UserRole;



  constructor(name: string, surname: string, email: string, password: string, role: UserRole) {
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}

