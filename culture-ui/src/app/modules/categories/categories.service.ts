import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HandleError, HttpErrorHandler } from '../../core/services/http-error-handler.service';

import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { CategoriesPage, Category } from './categories';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlLWFwcCIsInN1YiI6InVzZXIxQGdtYWlsLmNvbSIsImF1ZCI6IndlYiIsImlhdCI6MTYwODA0NzQ4MCwiZXhwIjoxNjA5ODQ3NDgwfQ.ueAGubG7bsyVoaxoFUTlFgzWNMZ-9QpTBBdETc9yLv9lWaAav5yLHSUWWCmWtFkpgQIHntZvej1vuENVLbeghg'
  })
};

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  categoriesUrl = `${environment.apiUrl}/api/categories`;
  private handleError: HandleError;

  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) { 
    this.handleError = httpErrorHandler.createHandleError('CategoryService');
  }

  getCategory(id: number): Observable<Category> {
    const url = `${this.categoriesUrl}`;
    return this.http.get<Category>(url, httpOptions)
    .pipe(
      catchError(this.handleError<Category>('getCategory')
      )
    );
  }

  getCategories(page: number, limit: number): Observable<CategoriesPage> {
    const url = `${this.categoriesUrl}?page=${page}&limit=${limit}`;
    return this.http.get<CategoriesPage>(url, httpOptions)
      .pipe(
        catchError(this.handleError<CategoriesPage>('getCategories')
        )
      );
  }

  postCategory(name: string): Observable<Category> {
    const url = `${this.categoriesUrl}`;
    return this.http.post<Category>(url, {name: name},  httpOptions)
      .pipe(
        catchError(this.handleError<Category>('postCategory')
        )
      );
  }

  updateCategory(id: number, name: string): Observable<Category> {
    const url = `${this.categoriesUrl}/${id}`;
    return this.http.put<Category>(url, {id: id, name: name}, httpOptions)
      .pipe(
        catchError(this.handleError<Category>('putCategory')
        )
      );
  }

  deleteCategory(id: number): Observable<Category> {
    const url = `${this.categoriesUrl}/${id}`;
    return this.http.delete<Category>(url, httpOptions)
      .pipe(
        catchError(this.handleError<Category>('deleteCategory')
        )
      );
  }
}
