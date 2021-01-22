import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HandleError, HttpErrorHandler } from 'src/app/core/services/http-error-handler.service';
import { environment } from 'src/environments/environment';
import { SubcategoriesPage, Subcategory } from './subcategory';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlLWFwcCIsInN1YiI6InVzZXIxQGdtYWlsLmNvbSIsImF1ZCI6IndlYiIsImlhdCI6MTYwODA0NzQ4MCwiZXhwIjoxNjA5ODQ3NDgwfQ.ueAGubG7bsyVoaxoFUTlFgzWNMZ-9QpTBBdETc9yLv9lWaAav5yLHSUWWCmWtFkpgQIHntZvej1vuENVLbeghg'
  })
};

@Injectable({
  providedIn: 'root'
})
export class SubcategoriesService {
  subcategoriesUrl = `${environment.apiUrl}/api/categories`;
  private handleError: HandleError;

  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('SubcategoriesService');
  }

  deleteSubcategory(subcategory:any): Observable<{}> {
    const url = `${this.subcategoriesUrl}/${subcategory.categoryId}/subcategories/${subcategory.id}`;
    return this.http.delete(url, httpOptions)
    .pipe(
      catchError(this.handleError('deleteSubcategory'))
    );
  }

  updateSubcategory(subcategory: Subcategory): Observable<Subcategory> {
    const url = `${this.subcategoriesUrl}/${subcategory.categoryId}/subcategories/${subcategory.id}`;
    return this.http.put<Subcategory>(url, subcategory, httpOptions)
      .pipe(
        catchError(this.handleError<Subcategory>('putCategory'))
      );
  }

  addSubcategory(subcategory: Subcategory): Observable<void> {
    const url = `${this.subcategoriesUrl}/${subcategory.categoryId}/subcategories`;
    return this.http.post<void>(url, subcategory, httpOptions)
      .pipe(
        catchError(this.handleError<void>('postCategory'))
      );
  }

  getSubcategories(categoryId: number, page: number, limit: number): Observable<SubcategoriesPage> {
    const url = `${this.subcategoriesUrl}/${categoryId}/subcategories?page=${page}&limit=${limit}`;
    return this.http.get<SubcategoriesPage>(url, httpOptions)
      .pipe(
        catchError(this.handleError<SubcategoriesPage>('getSubcategories'))
      );
  }

  getSubcategoryNames(categoryId: number): Observable<Subcategory[]> {
    const url = `${this.subcategoriesUrl}/${categoryId}/subcategories/names`;
    return this.http.get<Subcategory[]>(url, httpOptions)
      .pipe(
        catchError(this.handleError('getSubcategoryNames', []))
      );
  }
}
