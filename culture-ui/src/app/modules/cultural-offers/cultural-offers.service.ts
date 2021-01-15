import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HandleError, HttpErrorHandler } from '../../core/services/http-error-handler.service';

import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CulturalOffer, CulturalOfferLocation, LocationRange } from './cultural-offer';
import { environment } from 'src/environments/environment';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
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

  getCulturalOfferLocations(locationRange: LocationRange, categoryId: number, subcategoryId: number): Observable<CulturalOfferLocation[]> {
    let url = `${this.culturalOffersUrl}/locations`;
    url += `?latitudeFrom=${locationRange.latitudeFrom}&latitudeTo=${locationRange.latitudeTo}`;
    url += `&longitudeFrom=${locationRange.longitudeFrom}&longitudeTo=${locationRange.longitudeTo}`;
    if (categoryId)
      url += `&categoryId=${categoryId}`;
    if (subcategoryId)
      url += `&subcategoryId=${subcategoryId}`;
    return this.http.get<CulturalOfferLocation[]>(url, httpOptions)
      .pipe(
        catchError(this.handleError('getCulturalOfferLocations', []))
      );
  }

  subscribeToCulturalOffer(id: number): Observable<{}> {
    const url = `${this.culturalOffersUrl}/${id}/subscriptions`;
    return this.http.post(url, null, httpOptions)
      .pipe(
        catchError(this.handleError('subscribeToCulturalOffer'))
      );
  }

  unsubscribeFromCulturalOffer(id: number): Observable<{}> {
    const url = `${this.culturalOffersUrl}/${id}/subscriptions`;
    return this.http.delete(url, httpOptions)
      .pipe(
        catchError(this.handleError('unsubscribeFromCulturalOffer'))
      );
  }

  getSubscribed(id: number): Observable<boolean> {
    const url = `${this.culturalOffersUrl}/${id}/subscriptions`;
    return this.http.get<boolean>(url, httpOptions)
      .pipe(
        catchError(this.handleError<boolean>('isSubscribed'))
      )
  }
}
