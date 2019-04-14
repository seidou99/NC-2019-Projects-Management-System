import {Injectable} from '@angular/core';
import {User, UserAuthData} from '../models/user';
import {UserRole} from '../models/user-role';
import {HttpClient} from '@angular/common/http';
import {loginURI} from '../configs/conf';
import {map} from 'rxjs/operators';
import {TokenResponse} from '../models/tokenResponse';
import {tokenKey} from '@angular/core/src/view';
import {Observable} from 'rxjs';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly tokenKey: string = 'token';
  private token = '';

  constructor(private http: HttpClient) {
  }

  login(credentials: UserAuthData, rememberPassword: boolean): Observable<TokenResponse> {
    return this.http.post<TokenResponse>(loginURI, credentials).pipe(
      map((tokenResponse: TokenResponse) => {
        localStorage.removeItem(this.tokenKey);
        if (rememberPassword) {
          localStorage.setItem(this.tokenKey, tokenResponse.token);

          console.log('in storage ' + localStorage.getItem(this.tokenKey));

        }
        this.token = tokenResponse.token;
        return tokenResponse;
      })
    );
  }

  logout() {
    this.token = '';
    localStorage.removeItem(this.tokenKey);
  }

  private getTokenPayload(): any {
    if (this.token) {
      this.token = localStorage.getItem(this.tokenKey);
    }
    return jwt_decode(this.token);
  }

  getUserRole(): UserRole {
    return this.getTokenPayload().scopes as UserRole;
  }

  getUserEmail(): string {
    return this.getTokenPayload().sub as string;
  }
}