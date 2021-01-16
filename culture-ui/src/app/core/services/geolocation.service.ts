import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { LocationRange } from 'src/app/modules/cultural-offers/cultural-offer';
import { environment } from 'src/environments/environment';
import { HandleError, HttpErrorHandler } from './http-error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class GeolocationService {
  geolocationApiUrl: string = `https://maps.googleapis.com/maps/api/geocode/json`;

  private handleError: HandleError;

  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('GeolocationService');
  }

  geocode(location: string): Observable<LocationRange> {
    const address = location.replace(" ", "%20");
    const url = `${this.geolocationApiUrl}?address=${address}&key=${environment.mapsApiKey}`;
    return this.http.get<any>(url)
      .pipe(
        catchError(this.handleError('geocode')),
        map((result) => {
          if (!result.results.length)
            throw new Error('Location not found.');
          const viewport = result.results[0].geometry.viewport;
          const locationRange: LocationRange = {
            latitudeFrom: viewport.southwest.lat,
            latitudeTo: viewport.northeast.lat,
            longitudeFrom: viewport.southwest.lng,
            longitudeTo: viewport.northeast.lng
          };
          return locationRange;
        })
      );
  }
}
