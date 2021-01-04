import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HandleError, HttpErrorHandler } from '../core/services/http-error-handler.service';

import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { RegisterRequest } from './register';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlLWFwcCIsInN1YiI6ImFkbWluMUBnbWFpbC5jb20iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MDkyNTMzNzcsImV4cCI6MTYxMTA1MzM3N30.f_9B7npSnsKWlD3cKmxeKXa_Nso4PGvSwOxbxt-Gw16fnyZ3Ho1gLNvWPMiuvCWeiBiuEH2Wf1bxuwYunH84eQ'
  })
};

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  categoriesUrl = `${environment.apiUrl}/api/auth/register`;
  private handleError: HandleError;

  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('RegisterService');
  }

  register(registerRequest: RegisterRequest): Observable<void> {
    const url = `${this.categoriesUrl}`;
    return this.http.post<void>(url, registerRequest, httpOptions)
      .pipe(
        catchError(this.handleError<void>('postRegisterRequest')
        )
      );
  }
}
