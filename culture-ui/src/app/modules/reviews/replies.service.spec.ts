import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { environment } from 'src/environments/environment';
import { RepliesService } from './replies.service';
import { mockReplyToAdd, mockEmptyCommentReplyToAdd, mockNegativeRatingReviewToAdd } from 'src/app/shared/testing/mock-data';

describe('RepliesService', () => {
  let service: RepliesService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  const offersUrl = `${environment.apiUrl}/api/cultural-offers`;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RepliesService]
    });
    injector = getTestBed();
    service = TestBed.inject(RepliesService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('addReply() should add valid reply', fakeAsync(() => {
    const culturalOfferId = 1;
    const reviewId = 1;

    const expectedResponse: HttpResponse<void> = new HttpResponse({
      status: 200,
      statusText: 'Ok'
    });

    let response: any;
    service.addReply(culturalOfferId, reviewId, mockReplyToAdd)
      .subscribe(result => response = result);

    const repliesUrl = `${offersUrl}/${culturalOfferId}/reviews/${reviewId}/replies`;
    const req = httpMock.expectOne(repliesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(expectedResponse);

    tick();

    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('addReply() should throw exception => invalid offer id', fakeAsync(() => {
    const culturalOfferId = -1;
    const reviewId = 1;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Cultural offer with id -1 does not exist',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.addReply(culturalOfferId, reviewId, mockReplyToAdd)
      .subscribe(result => response = result);

    const repliesUrl = `${offersUrl}/${culturalOfferId}/reviews/${reviewId}/replies`;
    const req = httpMock.expectOne(repliesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('addReply() should throw exception => invalid review id', fakeAsync(() => {
    const culturalOfferId = 1;
    const reviewId = -1;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Review with id -1 does not exist',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.addReply(culturalOfferId, reviewId, mockReplyToAdd)
      .subscribe(result => response = result);

    const repliesUrl = `${offersUrl}/${culturalOfferId}/reviews/${reviewId}/replies`;
    const req = httpMock.expectOne(repliesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('addReply() should throw exception => empty comment', fakeAsync(() => {
    const culturalOfferId = 1;
    const reviewId = 1;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Comment can not be empty',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.addReply(culturalOfferId, reviewId, mockEmptyCommentReplyToAdd)
      .subscribe(result => response = result);

    const repliesUrl = `${offersUrl}/${culturalOfferId}/reviews/${reviewId}/replies`;
    const req = httpMock.expectOne(repliesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('addReply() should throw exception => negative rating', fakeAsync(() => {
    const culturalOfferId = 1;
    const reviewId = 1;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Rating must be positive number',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.addReply(culturalOfferId, reviewId, mockNegativeRatingReviewToAdd)
      .subscribe(result => response = result);

    const repliesUrl = `${offersUrl}/${culturalOfferId}/reviews/${reviewId}/replies`;
    const req = httpMock.expectOne(repliesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));
});
