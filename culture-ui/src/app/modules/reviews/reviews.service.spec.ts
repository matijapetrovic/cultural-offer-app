import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
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
    const culturalOfferId: number = 1;
    const page: number = 0;
    const limit: number = 2;

    const mockReviews: ReviewPage = {
      data: [
        {
          id: 1,
          culturalOfferId: culturalOfferId,
          rating: 3.0,
          author: {
            id: 1,
            firstName: 'First name author',
            lastName: 'Last name author'
          },
          comment: 'Some text',
          images: ['image1', 'image2']
        },
        {
          id: 2,
          culturalOfferId: culturalOfferId,
          rating: 4.0,
          author: {
            id: 2,
            firstName: 'First name author 2',
            lastName: 'Last name author 2'
          },
          comment: 'Some other text',
          images: ['image1', 'image2']
        }
      ],
      links: new Map([['next', 'next-link'], ['self', 'self-link']])
    };

    let reviewPage: ReviewPage;
    service.getReviews(culturalOfferId, page, limit).subscribe((result) => reviewPage = result);

    let url = `${environment.apiUrl}/api/cultural-offers/${culturalOfferId}/reviews?page=${page}&limit=${limit}`;

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockReviews);

    tick();

    expect(reviewPage).toBeDefined();
    
    expect(reviewPage.data).toBeDefined();
    expect(reviewPage.data.length).toEqual(mockReviews.data.length);

    expect(reviewPage.links).toBeDefined();
    expect(reviewPage.links.has('self')).toBeTrue();
    expect(reviewPage.links.has('next')).toBeTrue();
    expect(reviewPage.links.has('prev')).toBeFalse();
  }));
});
