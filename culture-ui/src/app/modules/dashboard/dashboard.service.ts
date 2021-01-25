import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HandleError, HttpErrorHandler } from 'src/app/core/services/http-error-handler.service';
import { environment } from 'src/environments/environment';
import { SubscriptionsSubcategoriesPage } from './subscriptions-details';

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
  // subscribedSubcategoriesUrl
  subSubCatUrl = `${environment.apiUrl}/api/subscriptions/subcategories`;
  private handleError: HandleError;

  // private RegenerateData = new Subject<void>();
  // RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient, private httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('SubscriptionsService');
  }

  getSubscribedSubcategories(page: number, limit: number):
    Observable<SubscriptionsSubcategoriesPage> {
    const url = `${this.subSubCatUrl}?page=${page}&limit=${limit}`;
    return this.http.get<SubscriptionsSubcategoriesPage>(url, httpOptions)
      .pipe(catchError(this.handleError<SubscriptionsSubcategoriesPage>('getSubscribedSubcategories')));
  }
}
