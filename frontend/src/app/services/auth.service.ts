import {Injectable} from '@angular/core';
import {UserAuthData} from '../models/user';
import {UserRole} from '../models/user-role';
import {HttpClient} from '@angular/common/http';
import {loginURI} from '../configs/conf';
import {map} from 'rxjs/operators';
import {TokenResponse} from '../models/tokenResponse';
import {Observable} from 'rxjs';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly tokenKey: string = 'projects-management-token';
  private readonly authDataKey: string = 'projects-management-auth-data';

  constructor(private http: HttpClient) {
  }

  login(credentials: UserAuthData, rememberPassword: boolean): Observable<TokenResponse> {
    return this.http.post<TokenResponse>(loginURI, credentials).pipe(
      map((tokenResponse: TokenResponse) => {
        localStorage.setItem(this.tokenKey, tokenResponse.token);
        console.log('token in local storage ', localStorage.getItem(this.tokenKey));
        if (rememberPassword) {
          localStorage.setItem(this.authDataKey, JSON.stringify(credentials));
        } else {
          localStorage.removeItem(this.authDataKey);
        }
        return tokenResponse;
      })
    );
  }

  getUserCredentials(): UserAuthData {
    return JSON.parse(localStorage.getItem(this.authDataKey)) as UserAuthData;
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
  }

  private getTokenPayload(): any {
    return jwt_decode(localStorage.getItem(this.tokenKey));
  }

  public getToken(): string {
    return localStorage.getItem(this.tokenKey);
  }

  isTokenNotExpired(): boolean {
    return +this.getTokenPayload().exp * 1000 > new Date().valueOf();
  }

  getUserRole(): UserRole {
    return this.getTokenPayload().role as UserRole;
  }

  getUserId(): number {
    return +(this.getTokenPayload().jti as string);
  }
}
