import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HandleError, HttpErrorHandler } from 'src/app/core/services/http-error-handler.service';
import { environment } from 'src/environments/environment';
import { Category } from './category';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlLWFwcCIsInN1YiI6InVzZXIxQGdtYWlsLmNvbSIsImF1ZCI6IndlYiIsImlhdCI6MTYwODA0NzQ4MCwiZXhwIjoxNjA5ODQ3NDgwfQ.ueAGubG7bsyVoaxoFUTlFgzWNMZ-9QpTBBdETc9yLv9lWaAav5yLHSUWWCmWtFkpgQIHntZvej1vuENVLbeghg'
  })
};

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  categoriesUrl = `${environment.apiUrl}/api/categories`
  private handleError: HandleError;

  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('CategoriesService');
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoriesUrl, httpOptions)
      .pipe(
        catchError(this.handleError('getCategories', []))
      );
  }

}
