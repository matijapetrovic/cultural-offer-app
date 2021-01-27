import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { mockReviewPage, mockEmptyReviewPage, mockReviewToAdd, mockEmptyCommentReviewToAdd, mockNegativeRatingReviewToAdd } from 'src/app/shared/testing/mock-data';
import { environment } from 'src/environments/environment';
import { ReviewPage } from './review';

import { ReviewsService } from './reviews.service';

describe('ReviewsService', () => {
  let service: ReviewsService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  const offersUrl = `${environment.apiUrl}/api/cultural-offers`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReviewsService]
    });
    injector = getTestBed();
    service = TestBed.inject(ReviewsService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getReviews() should return some reviews', fakeAsync(() => {
    const culturalOfferId = 1;
    const page = 0;
    const limit = 2;

    let reviewPage: ReviewPage;
    service.getReviews(culturalOfferId, page, limit).subscribe((result) => reviewPage = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews?page=${page}&limit=${limit}`;

    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockReviewPage);

    tick();

    expect(reviewPage).toBeDefined();

    expect(reviewPage.data).toBeDefined();
    expect(reviewPage.data.length).toEqual(mockReviewPage.data.length);

    expect(reviewPage.links).toBeDefined();
    expect(reviewPage.links.has('self')).toBeTrue();
    expect(reviewPage.links.has('next')).toBeTrue();
    expect(reviewPage.links.has('prev')).toBeFalse();
  }));

  it('getReviews() should not return any reviews => offer does not contain reviews', fakeAsync(() => {
    // cultural offer with id 1000 does not have any reviews
    const culturalOfferId = 1000;
    const page = 0;
    const limit = 5;

    let reviewPage: ReviewPage;
    service.getReviews(culturalOfferId, page, limit).subscribe(result => reviewPage = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews?page=${page}&limit=${limit}`;

    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmptyReviewPage);

    tick();

    expect(reviewPage).toBeDefined();
    expect(reviewPage.data).toBeDefined();
    expect(reviewPage.data.length).toEqual(mockEmptyReviewPage.data.length);

    expect(reviewPage.links).toBeDefined();
    expect(reviewPage.links.size).toEqual(mockEmptyReviewPage.links.size);
    expect(reviewPage.links.has('prev')).toBeTrue();
    expect(reviewPage.links.has('self')).toBeTrue();
    expect(reviewPage.links.has('next')).toBeFalse();
  }));

  it('getReviews() should not return any reviews => empty page', fakeAsync(() => {
    const culturalOfferId = 1;
    // there are no reviews on page 1000
    const page = 1000;
    const limit = 5;

    let reviewPage: ReviewPage;
    service.getReviews(culturalOfferId, page, limit).subscribe(result => reviewPage = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews?page=${page}&limit=${limit}`;

    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmptyReviewPage);

    tick();

    expect(reviewPage).toBeDefined();
    expect(reviewPage.data).toBeDefined();
    expect(reviewPage.data.length).toEqual(mockEmptyReviewPage.data.length);

    expect(reviewPage.links).toBeDefined();
    expect(reviewPage.links.size).toEqual(mockEmptyReviewPage.links.size);
    expect(reviewPage.links.has('prev')).toBeTrue();
    expect(reviewPage.links.has('self')).toBeTrue();
    expect(reviewPage.links.has('next')).toBeFalse();
  }));

  it('getReviews() should throw exception => non existing offer id', fakeAsync(() => {
    // cultural offer with id -1 does not exist
    const culturalOfferId = -1;
    const page = 0;
    const limit = 5;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Cultural offer does not exist',
      status: 404,
      statusText: 'Not Found'
    });

    let response: any;
    service.getReviews(culturalOfferId, page, limit).subscribe(result => response = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews?page=${page}&limit=${limit}`;

    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('GET');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('getReviews() should throw exception => invalid page number', fakeAsync(() => {
    const culturalOfferId = 1;
    const page = -1;
    const limit = 5;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Invalid page number',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.getReviews(culturalOfferId, page, limit).subscribe(result => response = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews?page=${page}&limit=${limit}`;
    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('GET');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('getReviews() should throw exception => invalid limit number', fakeAsync(() => {
    const culturalOfferId = 1;
    const page = 1;
    const limit = -5;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Invalid limit number',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.getReviews(culturalOfferId, page, limit).subscribe(result => response = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews?page=${page}&limit=${limit}`;
    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('GET');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('addReview() should add valid review', fakeAsync(() => {
    const culturalOfferId = 1;

    let response: any;
    service.addReview(mockReviewToAdd, culturalOfferId).subscribe(result => response = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews`;
    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('POST');
    req.flush({}, { status: 200, statusText: 'Ok' });

    tick();

    expect(response).toBeTruthy();
  }));

  it('addReview() should throw exception => non existing cultural offer id', fakeAsync(() => {
    const culturalOfferId = -1;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Cultural offer with id -1 does not exist',
      status: 404,
      statusText: 'Not found'
    });

    let response: any;
    service.addReview(mockReviewToAdd, culturalOfferId).subscribe(result => response = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews`;
    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('POST');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('addReview() should throw exception => empty comment', fakeAsync(() => {
    const culturalOfferId = 1;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Comment must not be empty',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.addReview(mockEmptyCommentReviewToAdd, culturalOfferId).subscribe(result => response = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews`;
    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('POST');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('addReview() should throw exception => negative rating', fakeAsync(() => {
    const culturalOfferId = 1;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Rating must be positive number',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.addReview(mockNegativeRatingReviewToAdd, culturalOfferId).subscribe(result => response = result);

    const reviewsUrl = `${offersUrl}/${culturalOfferId}/reviews`;
    const req = httpMock.expectOne(reviewsUrl);
    expect(req.request.method).toBe('POST');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));
});
