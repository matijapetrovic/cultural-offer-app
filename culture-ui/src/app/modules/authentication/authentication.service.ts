import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { User } from './user';
import { Router } from '@angular/router';
import { RegisterRequest } from './register';
import { HandleError, HttpErrorHandler } from 'src/app/core/services/http-error-handler.service';

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

  constructor(private http: HttpClient, private router: Router, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('AuthenticationService');
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return this.currentUserSubject.value != null;
  }

  login(email: string, password: string) {
    return this.http.post<any>(`${this.authenticationUrl}/login`, { email, password })
      .pipe(map(user => {
        if (user && user.token) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }

        return user;
      }));
  }

  logout() {
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
}