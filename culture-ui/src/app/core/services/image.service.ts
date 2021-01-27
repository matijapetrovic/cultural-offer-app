import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { HandleError, HttpErrorHandler } from './http-error-handler.service';

const httpOptions = {
  headers: new HttpHeaders({
    // 'Content-Type': 'multipart/form-data'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private imagesUrl = `${environment.apiUrl}/api/images`;
  private handleError: HandleError;

  constructor(private http: HttpClient, private httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('ImagesService');
  }

  addImages(imagesToAdd: FormData): Observable<Array<number>> {
    return this.http
      .post<Array<number>>(this.imagesUrl, imagesToAdd, httpOptions)
      .pipe(catchError(this.handleError<Array<number>>('postImages')));

  }
}
