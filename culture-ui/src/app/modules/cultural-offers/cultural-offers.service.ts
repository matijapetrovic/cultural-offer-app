import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HandleError, HttpErrorHandler } from '../../core/services/http-error-handler.service';

import { Observable, of, scheduled } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { CulturalOffer, CulturalOfferLocation, CulturalOffersPage, CulturalOfferToAdd, CulturalOfferView, LocationRange } from './cultural-offer';
import { environment } from 'src/environments/environment';
import { MessageService } from 'primeng/api';

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

  constructor(private http: HttpClient, private messageService: MessageService, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('CulturalOffersService');
  }

  addCulturalOffer(culturalOffer: CulturalOfferToAdd): Observable<void> {
    const url =  `${this.culturalOffersUrl}`;
    return this.http.post<void>(url, culturalOffer)
    .pipe(
      catchError(this.handleError<void>('postCulturalOffer'))
    );
  }

  updateCulturalOffer(culturalOffer: CulturalOfferToAdd): Observable<CulturalOfferView> {
    const url =  `${this.culturalOffersUrl}/${culturalOffer.id}`;
    return this.http.put<CulturalOfferView>(url, culturalOffer)
    .pipe(
      catchError(this.handleError<CulturalOfferView>('putCulturalOffer'))
    );
  }

  deleteCulturalOffer(culturalOffer:any): Observable<{}> {
    const url =  `${this.culturalOffersUrl}/${culturalOffer.id}`;
    return this.http.delete(url)
    .pipe(
      catchError(this.handleError('deleteCulturalOffer'))
    )
  }

  getCulturaOffers(page: number, limit: number): Observable<CulturalOffersPage> {
    const url =  `${this.culturalOffersUrl}?page=${page}&limit=${limit}`;
    return this.http.get<CulturalOffersPage>(url)
    .pipe(
      catchError(this.handleError<CulturalOffersPage>('getCulturalOffers'))
    )
  }

  getCulturalOffer(id: number): Observable<CulturalOffer> {
    const url = `${this.culturalOffersUrl}/${id}`;
    return this.http.get<CulturalOffer>(url, httpOptions)
      .pipe(
        catchError(this.handleError<CulturalOffer>('getCulturalOffer'))
      );
  }

  getCulturalOfferLocations(locationRange: LocationRange, categoryId: number, subcategoryId: number): Observable<CulturalOfferLocation[]> {
    let params: HttpParams =
      new HttpParams()
        .append('latitudeFrom', locationRange.latitudeFrom.toString())
        .append('latitudeTo', locationRange.latitudeTo.toString())
        .append('longitudeFrom', locationRange.longitudeFrom.toString())
        .append('longitudeTo', locationRange.longitudeTo.toString());
    if (categoryId) {
      params = params.append('categoryId', categoryId.toString());
    }
    if (subcategoryId) {
      params = params.append('subcategoryId', subcategoryId.toString());
    }

    const url = `${this.culturalOffersUrl}/locations`;
    return this.http.get<CulturalOfferLocation[]>(url, {...httpOptions, params})
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
    return this.http.get<void>(url, {...httpOptions, observe: 'response'})
      .pipe(
        map((response) => response.status === 204 ? true : false),
        catchError((err) => of(false))
      );
  }
}
