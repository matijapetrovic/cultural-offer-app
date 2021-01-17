import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { environment } from 'src/environments/environment';
import { Review, ReviewPage } from '../reviews/review';
import { NewsPage } from './news';

import { NewsService } from './news.service';

describe('NewsService', () => {
  let service: NewsService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [NewsService]
    });
    injector = getTestBed();
    service = TestBed.inject(NewsService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getNews() should return some news', fakeAsync(() => {
    const culturalOfferId: number = 1;
    const page: number = 0;
    const limit: number = 2;

    const mockReviews: NewsPage = {
      data: [
        {
          id: 1,
          culturalOfferId: culturalOfferId,
          title: 'Some title',
          postedDate: 'date',
          author: {
            id: 1,
            firstName: 'First name author',
            lastName: 'Last name author'
          },
          text: 'Some text',
          images: ['image1', 'image2']
        },
        {
          id: 2,
          culturalOfferId: culturalOfferId,
          title: 'Some title 2',
          postedDate: 'date 2',
          author: {
            id: 2,
            firstName: 'First name author 2',
            lastName: 'Last name author 2'
          },
          text: 'Some other text',
          images: ['image1', 'image2']
        }
      ],
      links: new Map([['next', 'next-link'], ['self', 'self-link']])
    };

    let newsPage: NewsPage;
    service.getNews(culturalOfferId, page, limit).subscribe((result) => newsPage = result);
    
    let url = `${environment.apiUrl}/api/cultural-offers/${culturalOfferId}/news?page=${page}&limit=${limit}`;

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockReviews);

    tick();

    expect(newsPage).toBeDefined();
    
    expect(newsPage.data).toBeDefined();
    expect(newsPage.data.length).toEqual(mockReviews.data.length);

    expect(newsPage.links).toBeDefined();
    expect(newsPage.links.has('self')).toBeTrue();
    expect(newsPage.links.has('next')).toBeTrue();
    expect(newsPage.links.has('prev')).toBeFalse();
  }));
  
});
