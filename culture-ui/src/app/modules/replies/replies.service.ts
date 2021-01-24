import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HandleError, HttpErrorHandler } from 'src/app/core/services/http-error-handler.service';
import { environment } from 'src/environments/environment';
import { ReplyToAdd } from './reply';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};


@Injectable({
  providedIn: 'root'
})
export class RepliesService {
  private offersUrl = `${environment.apiUrl}/api/cultural-offers`;
  private handleError: HandleError;

  constructor(private http: HttpClient, private httpErrorhandler: HttpErrorHandler) {
    this.handleError = httpErrorhandler.createHandleError('RepliesService');
  }

  addReply(
    culturalOfferId: number,
    reviewId: number,
    replyToAdd: ReplyToAdd
  ): Observable<void> {
    const url = `${this.offersUrl}/${culturalOfferId}/reviews/${reviewId}/replies`;
    console.log(url);

    return this.http
      .post<void>(url, replyToAdd, httpOptions)
      .pipe(catchError(this.handleError<void>('postReply')));
  }
}
