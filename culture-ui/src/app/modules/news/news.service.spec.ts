import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { mockCulturalOfferView, mockNews, mockNewsPage, mockNewsToAdd, mockNewsView } from 'src/app/shared/testing/mock-data';
import { environment } from 'src/environments/environment';
import { News, NewsPage, NewsToAdd, NewsView } from './news';

import { NewsService } from './news.service';

describe('NewsService', () => {
  let service: NewsService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  const errorHandler = jasmine.createSpyObj('errorHandler', ['handleError']);

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
    const culturalOfferId = 1;
    const page = 0;
    const limit = 2;

    let newsPage: NewsPage;
    service.getNews(culturalOfferId, page, limit).subscribe((result) => newsPage = result);

    const url = `${environment.apiUrl}/api/cultural-offers/${culturalOfferId}/news?page=${page}&limit=${limit}`;

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

  // ADD NEWS

  it('addNews() should add valid news', fakeAsync(() => {
  let response: any;
  const url = `${environment.apiUrl}/api/cultural-offers/${mockNewsToAdd.culturalOfferId}/news`;

  service.addNews(mockNewsToAdd.culturalOfferId, mockNewsToAdd).subscribe(data => { response = data; });

  const req = httpMock.expectOne(url);
  expect(req.request.method).toBe('POST');
  req.flush(mockNewsView);

  tick();

  expect(response).toBeTruthy();
}));

  it('addNews() should throw offer does not exists', fakeAsync(() => {
  let response: any;
  const errorMessage = 'Offer already exists!';
  const url = `${environment.apiUrl}/api/cultural-offers/${-1}/news`;

  service.addNews(-1, mockNewsToAdd).subscribe(data => { response = data; });

  const req = httpMock.expectOne(url);
  expect(req.request.method).toBe('POST');
  req.flush(mockNewsView);
  errorHandler.handleError.and.callFake(() => {
    throw errorMessage;
  });

  tick();

  expect(errorHandler.handleError).toThrow(errorMessage);
}));

// UPDATE NEWS

  it('updateNews() should update valid news', fakeAsync(() => {
  let news: NewsView;
  const url = `${environment.apiUrl}/api/cultural-offers/${mockNewsToAdd.culturalOfferId}/news`;

  service.updateNews(mockNewsToAdd.culturalOfferId, mockNewsToAdd).subscribe();

  const req = httpMock.expectOne(url + `/${mockNewsToAdd.id}`);
  expect(req.request.method).toBe('PUT');
  req.flush(mockNewsView);

  tick();

  service.getOne(mockNewsToAdd.culturalOfferId, mockCulturalOfferView).subscribe(data => news = data);
  const reqUpdate = httpMock.expectOne(url + `/${mockCulturalOfferView.id}`);
  expect(reqUpdate.request.method).toBe('GET');
  reqUpdate.flush(mockNewsView);

  tick();

  expect(news).toBeTruthy();
  expect(news.id).toEqual(mockNewsView.id);
  expect(news.title).toEqual(mockNewsView.title);
  expect(news.text).toEqual(mockNewsView.text);
}));

  it('updateNews() should throw invalid offer Id', fakeAsync(() => {
  const errorMessage = 'Invalid Offer Id';

  const updatedMockNews: NewsToAdd = {
    id: 1,
    culturalOfferId: -1,
    title: 'string',
    text: 'string',
    images: [1, 2]
  };

  const url = `${environment.apiUrl}/api/cultural-offers/${updatedMockNews.culturalOfferId}/news`;

  service.updateNews(updatedMockNews.culturalOfferId, updatedMockNews).subscribe();

  const req = httpMock.expectOne(url + `/${updatedMockNews.id}`);
  expect(req.request.method).toBe('PUT');
  req.flush(mockCulturalOfferView);
  errorHandler.handleError.and.callFake(() => {
    throw errorMessage;
  });

  tick();

  expect(errorHandler.handleError).toThrow(errorMessage);
}));

  it('updateNews() should throw invalid offer Id', fakeAsync(() => {
  const errorMessage = 'Invalid News Id';

  const updatedMockNews: NewsToAdd = {
    id: -1,
    culturalOfferId: 1,
    title: 'string',
    text: 'string',
    images: [1, 2]
  };

  const url = `${environment.apiUrl}/api/cultural-offers/${updatedMockNews.culturalOfferId}/news`;

  service.updateNews(updatedMockNews.culturalOfferId, updatedMockNews).subscribe();

  const req = httpMock.expectOne(url + `/${updatedMockNews.id}`);
  expect(req.request.method).toBe('PUT');
  req.flush(mockCulturalOfferView);
  errorHandler.handleError.and.callFake(() => {
    throw errorMessage;
  });

  tick();

  expect(errorHandler.handleError).toThrow(errorMessage);
}));

// DELETE NEWS

  it('deleteNewsr() should delete valid news', fakeAsync(() => {

  service.deleteNews(mockNewsToAdd.culturalOfferId, 1).subscribe();
  const url = `${environment.apiUrl}/api/cultural-offers/${mockNewsToAdd.culturalOfferId}/news`;

  const req = httpMock.expectOne(url + '/1');
  expect(req.request.method).toBe('DELETE');
  req.flush({});
}));

  it('deleteNewsr() should throw invalid offer Id', fakeAsync(() => {

    const errorMessage = 'Invalid Offer Id';

    service.deleteNews(-1, 1).subscribe();
    const url = `${environment.apiUrl}/api/cultural-offers/-1/news`;

    const req = httpMock.expectOne(url + '/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));


  it('deleteNewsr() should throw invalid news Id', fakeAsync(() => {

    const errorMessage = 'Invalid news Id';

    service.deleteNews(1, -1).subscribe();
    const url = `${environment.apiUrl}/api/cultural-offers/1/news`;

    const req = httpMock.expectOne(url + '/-1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));


  // GET ONE

  it('getOne() should return valid news', fakeAsync(() => {

    let news: NewsView;
    const url = `${environment.apiUrl}/api/cultural-offers/1/news`;


    service.getOne(1, mockNewsView).subscribe(response => { news = response; });

    const req = httpMock.expectOne(url + `/${mockNewsView.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockNewsView);

    tick();

    expect(news).toBeTruthy();
    expect(news.id).toEqual(mockNewsView.id);
    expect(news.title).toEqual(mockNewsView.title);
    expect(news.text).toEqual(mockNewsView.text);
  }));

  it('getOne() should throw Invalid offer Id', fakeAsync(() => {

    const errorMessage = 'Invalid offer Id';
    let news: NewsView;
    const url = `${environment.apiUrl}/api/cultural-offers/-1/news`;


    service.getOne(-1 , mockNewsView).subscribe(response => { news = response; });

    const req = httpMock.expectOne(url + `/${mockNewsView.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockNewsView);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  it('getOne() should throw Invalid news Id', fakeAsync(() => {

    const errorMessage = 'Invalid news Id';
    let news: NewsView;
    const url = `${environment.apiUrl}/api/cultural-offers/1/news`;

    const updatedMockNews: NewsToAdd = {
      id: -1,
      culturalOfferId: 1,
      title: 'string',
      text: 'string',
      images: [1, 2]
    };


    service.getOne(1, updatedMockNews).subscribe(response => { news = response; });

    const req = httpMock.expectOne(url + `/${updatedMockNews.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockNewsView);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

});
