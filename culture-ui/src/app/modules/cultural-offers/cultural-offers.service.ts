import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HandleError, HttpErrorHandler } from '../../core/services/http-error-handler.service';

import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CulturalOffer } from './cultural-offer';
import { environment } from 'src/environments/environment';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlLWFwcCIsInN1YiI6InVzZXIxQGdtYWlsLmNvbSIsImF1ZCI6IndlYiIsImlhdCI6MTYwNzk3MDA4NSwiZXhwIjoxNjA3OTcxODg1fQ.xEmzweS3VEA3NmOxnav_7UWNMVYwdG3GmUP-DViDWHhvemZ4raoKzYdoUhyZZQqCxAN5CGQYqQviP1sElIHoow'
  })
};


@Injectable({
  providedIn: 'root'
})
export class CulturalOffersService {
  culturalOffersUrl = `${environment.apiUrl}/api/cultural-offers`;
  private handleError: HandleError;

  constructor(private http: HttpClient, httpErrorHandler : HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('CulturalOffersService');
  }

  getCulturalOffer(id: number): Observable<CulturalOffer> {
    const url = `${this.culturalOffersUrl}/${id}`;
    return this.http.get<CulturalOffer>(url, httpOptions)
      .pipe(
        catchError(this.handleError<CulturalOffer>('getCulturalOffer'))
      );
  }
}
