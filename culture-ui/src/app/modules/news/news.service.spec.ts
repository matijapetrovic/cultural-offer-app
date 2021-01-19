import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { mockNewsPage } from 'src/app/shared/testing/mock-data';
import { environment } from 'src/environments/environment';
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

    let newsPage: NewsPage;
    service.getNews(culturalOfferId, page, limit).subscribe((result) => newsPage = result);
    
    let url = `${environment.apiUrl}/api/cultural-offers/${culturalOfferId}/news?page=${page}&limit=${limit}`;

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockNewsPage);

    tick();

    expect(newsPage).toBeDefined();
    
    expect(newsPage.data).toBeDefined();
    expect(newsPage.data.length).toEqual(mockNewsPage.data.length);

    expect(newsPage.links).toBeDefined();
    expect(newsPage.links.has('self')).toBeTrue();
    expect(newsPage.links.has('next')).toBeTrue();
    expect(newsPage.links.has('prev')).toBeFalse();
  }));
  
});
