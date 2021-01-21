import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HandleError, HttpErrorHandler } from 'src/app/core/services/http-error-handler.service';
import { environment } from 'src/environments/environment';
import { CategoriesPage, Category } from './category';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlLWFwcCIsInN1YiI6ImFkbWluMUBnbWFpbC5jb20iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MDkyNTMzNzcsImV4cCI6MTYxMTA1MzM3N30.f_9B7npSnsKWlD3cKmxeKXa_Nso4PGvSwOxbxt-Gw16fnyZ3Ho1gLNvWPMiuvCWeiBiuEH2Wf1bxuwYunH84eQ'
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
    const url = `${this.categoriesUrl}/${id}`;
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

  getCategoryNames(): Observable<Category[]> {
    const url = `${this.categoriesUrl}/names`;
    return this.http.get<Category[]>(url, httpOptions)
      .pipe(
        catchError(this.handleError('getCategoryNames', []))
      );
  }

  addCategory(category: Category): Observable<void> {
    const url = `${this.categoriesUrl}`;
    return this.http.post<void>(url, category, httpOptions)
      .pipe(
        catchError(this.handleError<void>('postCategory')
        )
      );
  }

  updateCategory(category: Category): Observable<Category> {
    const url = `${this.categoriesUrl}/${category.id}`;
    return this.http.put<Category>(url, {name: category.name}, httpOptions)
      .pipe(
        catchError(this.handleError<Category>('putCategory')
        )
      );
  }

  deleteCategory(id: number): Observable<{}> {
    const url = `${this.categoriesUrl}/${id}`;
    return this.http.delete(url, httpOptions)
      .pipe(
        catchError(this.handleError('deleteCategory'))
      );
  }

}
