import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  HandleError,
  HttpErrorHandler
} from '../../core/services/http-error-handler.service';

import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Review, ReviewPage, ReviewToAdd } from './review';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ReviewsService {
  offersUrl = `${environment.apiUrl}/api/cultural-offers`;
  private handleError: HandleError;

  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('ReviewsService');
  }

  getReviews(
    culturalOfferId: number,
    page: number,
    limit: number
  ): Observable<ReviewPage> {
    const params: HttpParams = new HttpParams()
      .append('page', page.toString())
      .append('limit', limit.toString());

    const url = `${this.offersUrl}/${culturalOfferId}/reviews`;
    return this.http
      .get<ReviewPage>(url, { ...httpOptions, params })
      .pipe(catchError(this.handleError<ReviewPage>('getReviews')));
  }

  addReview(
    reviewToAdd: ReviewToAdd,
    culturalOfferId: number
  ): Observable<void> {
    const url = `${this.offersUrl}/${culturalOfferId}/reviews`;
    return this.http
      .post<void>(url, reviewToAdd, httpOptions)
      .pipe(catchError(this.handleError<void>('postReview')));
  }
}
