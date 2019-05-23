import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';
import {UserRole} from '../models/user-role';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private auth: AuthService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles = route.data.expectedRoles as UserRole[];
    let actualRole: UserRole;
    try {
      actualRole = this.auth.getUserRole();
    } catch (_) {
      this.auth.logout();
      this.router.navigate(['login'], {state: {reason: 'You need to log in'}});
      return false;
    }
    if (expectedRoles.findIndex((v: UserRole) => v === actualRole) < 0) {
      this.auth.logout();
      this.router.navigate(['login'], {state: {reason: `You can't use this functionality with your role`}});
      return false;
    } else if (!this.auth.isTokenNotExpired()) {
      this.auth.logout();
      this.router.navigate(['login'], {state: {reason: 'Your token expired, log in'}});
      return false;
    } else {
      return true;
    }
  }
}
