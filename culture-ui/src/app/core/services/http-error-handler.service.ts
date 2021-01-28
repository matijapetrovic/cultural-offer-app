import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { MessageService } from 'primeng/api';

export type HandleError =
  <T> (operation?: string, result?: T) => (error: HttpErrorResponse) => Observable<T>;

/** Handles HttpClient errors */
@Injectable({
  providedIn: 'root'
})
export class HttpErrorHandler {
  constructor(private messageService: MessageService) { }

  /** Create curried handleError function that already knows the service name */
  createHandleError = (serviceName = '') => {
    return <T>(operation = 'operation', result = {} as T) =>
      this.handleError(serviceName, operation, result);
  }

  /**
   * Returns a function that handles Http operation failures.
   * This error handler lets the app continue to run as if no error occurred.
   * @param serviceName = name of the data service that attempted the operation
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  handleError<T>(serviceName = '', operation = 'operation', result = {} as T): any {

    return (error: HttpErrorResponse): Observable<T> => {
      console.error(error);

      this.messageService.add({ severity: 'error', summary: 'Error!', detail: error.error.message});

      // Let the app keep running by returning a safe result.
      return throwError( '' );
    };

  }
}
