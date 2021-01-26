import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HandleError, HttpErrorHandler } from '../../core/services/http-error-handler.service';

import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { NewsPage } from './news';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  newsUrl = `${environment.apiUrl}/api/cultural-offers`;
  private handleError: HandleError;

  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('NewsService');
  }

  getNews(culturalOfferId: number, page: number, limit: number): Observable<NewsPage> {
    const params: HttpParams =
      new HttpParams()
        .append('page', page.toString())
        .append('limit', limit.toString());

    const url = `${this.newsUrl}/${culturalOfferId}/news`;
    return this.http.get<NewsPage>(url, { ...httpOptions, params })
      .pipe(
        catchError(this.handleError<NewsPage>('getNews'))
      );
  }

  deleteNews(culturalOfferId: number, news: any): Observable<{}> {
    const url = `${this.newsUrl}/${culturalOfferId}/news/${news.id}`;
    return this.http.delete(url, httpOptions)
      .pipe(
        catchError(this.handleError('deleteNews'))
      );
  }
}
