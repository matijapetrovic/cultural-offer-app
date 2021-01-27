import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { User } from './user';
import { Router } from '@angular/router';
import { RegisterRequest } from './register';
import { HandleError, HttpErrorHandler } from 'src/app/core/services/http-error-handler.service';
import { Role } from './role';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};


@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  authenticationUrl = `${environment.apiUrl}/api/auth`;

  private handleError: HandleError;

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  private getUserFromLocalStorage(): User {
    const user = JSON.parse(localStorage.getItem('currentUser'));
    if (!user) {
      return null;
    }
    user.role = user.role.map((role: string) => Role[role]);
    return user;
  }

  constructor(private http: HttpClient, private router: Router, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('AuthenticationService');
    this.currentUserSubject = new BehaviorSubject<User>(this.getUserFromLocalStorage());
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return this.currentUserSubject.value != null;
  }

  login(email: string, password: string): Observable<User> {
    return this.http.post<User>(`${this.authenticationUrl}/login`, { email, password })
      .pipe(
        catchError(this.handleError<User>('login')),
        tap(userInfo => {
          if (userInfo && userInfo.token) {
            localStorage.setItem('currentUser', JSON.stringify(userInfo));
            this.currentUserSubject.next(this.getUserFromLocalStorage());
          }
        }));
  }

  logout(): void {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['']);
  }

  register(registerRequest: RegisterRequest): Observable<void> {
    const url = `${this.authenticationUrl}/register`;
    return this.http.post<void>(url, registerRequest, httpOptions)
      .pipe(
        catchError(this.handleError<void>('postRegisterRequest')
        )
      );
  }

  activate(id: number): Observable<void> {
    const url = `${this.authenticationUrl}/activate/${id}`;
    return this.http.post<void>(url, httpOptions)
      .pipe(
        catchError(this.handleError<void>('activateAccount')
        )
      );
  }
}
