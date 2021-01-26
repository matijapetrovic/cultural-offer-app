import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HandleError, HttpErrorHandler } from '../../core/services/http-error-handler.service';

import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { NewsPage, NewsToAdd, NewsView } from './news';

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

  addNews(culturalOfferId: number, news: NewsToAdd): Observable<void> {
    const url =  `${this.newsUrl}/${culturalOfferId}/news`;
    return this.http.post<void>(url, news)
    .pipe(
      catchError(this.handleError<void>('postNews'))
    );
  }

  updateNews(culturalOfferId: number, news: NewsToAdd): Observable<NewsView> {
    const url =  `${this.newsUrl}/${culturalOfferId}/news/${news.id}`;
    return this.http.put<NewsView>(url, news)
    .pipe(
      catchError(this.handleError<NewsView>('putNews'))
    );
  }

  deleteNews(culturalOfferId: number, newsId: number): Observable<{}> {
    const url = `${this.newsUrl}/${culturalOfferId}/news/${newsId}`;
    return this.http.delete(url, httpOptions)
      .pipe(
        catchError(this.handleError('deleteNews'))
      );
  }
}
