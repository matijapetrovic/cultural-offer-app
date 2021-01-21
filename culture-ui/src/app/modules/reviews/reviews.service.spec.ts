import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { mockReviewPage } from 'src/app/shared/testing/mock-data';
import { environment } from 'src/environments/environment';
import { ReviewPage } from './review';

import { ReviewsService } from './reviews.service';

describe('ReviewsService', () => {
  let service: ReviewsService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

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

    const url = `${environment.apiUrl}/api/cultural-offers/${culturalOfferId}/reviews?page=${page}&limit=${limit}`;

    const req = httpMock.expectOne(url);
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
});
