import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HandleError, HttpErrorHandler } from 'src/app/core/services/http-error-handler.service';
import { environment } from 'src/environments/environment';
import { SubscribedOffer, SubscribedSubcategoriesPage } from './subscriptions-details';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    observe: 'response'
  })
};

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private subscriptionsUrl = `${environment.apiUrl}/api/subscriptions`;
  private handleError: HandleError;

  // private RegenerateData = new Subject<void>();
  // RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient, private httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('SubscriptionsService');
  }

  getSubscribedSubcategories(page: number, limit: number):
    Observable<SubscribedSubcategoriesPage> {
    const url = `${this.subscriptionsUrl}/subcategories?page=${page}&limit=${limit}`;
    return this.http.get<SubscribedSubcategoriesPage>(url, httpOptions)
      .pipe(catchError(this.handleError<SubscribedSubcategoriesPage>('getSubscribedSubcategories')));
  }

  getSubscribedOffers(categoryId: number, subcategoryId: number): Observable<SubscribedOffer[]> {
    const url = `${this.subscriptionsUrl}?categoryId=${categoryId}&subcategoryId=${subcategoryId}`;
    return this.http.get<SubscribedOffer[]>(url, httpOptions)
      .pipe(catchError(this.handleError<SubscribedOffer[]>('getSubscribedOffers')));
  }
}
