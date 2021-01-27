import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed, getTestBed, tick, fakeAsync } from '@angular/core/testing';
import {
  mockSubscribedSubcategoriesPage,
  mockEmptySubscribedSubcategoriesPage,
  mockSubscribedOffersPage,
  mockEmptySubscribedOffersPage
} from 'src/app/shared/testing/mock-data';
import { environment } from 'src/environments/environment';
import { DashboardService } from './dashboard.service';
import { SubscribedOfferPage, SubscribedSubcategoriesPage } from './subscriptions-details';

describe('DashboardService', () => {
  let service: DashboardService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  const subscriptionsUrl = `${environment.apiUrl}/api/subscriptions`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DashboardService]
    });
    injector = getTestBed();
    service = TestBed.inject(DashboardService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getSubscribedSubcategories() should return some subscribed subcategories', fakeAsync(() => {
    const page = 0;
    const limit = 5;

    let subSubCatPage: SubscribedSubcategoriesPage;
    service.getSubscribedSubcategories(page, limit).subscribe(result => subSubCatPage = result);

    const url = `${subscriptionsUrl}/subcategories?page=${page}&limit=${limit}`;
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubscribedSubcategoriesPage);

    tick();

    expect(subSubCatPage).toBeDefined();

    expect(subSubCatPage.data).toBeDefined();
    expect(subSubCatPage.data.length).toEqual(mockSubscribedSubcategoriesPage.data.length);

    expect(subSubCatPage.links).toBeDefined();
    expect(subSubCatPage.links.has('self')).toBeTrue();
    expect(subSubCatPage.links.has('next')).toBeTrue();
    expect(subSubCatPage.links.has('prev')).toBeFalse();
  }));

  it('getSubscribedSubcategories() should not return any subscribed subcategories => empty page', fakeAsync(() => {
    const page = 1000;
    const limit = 5;

    let subSubCatPage: SubscribedSubcategoriesPage;
    service.getSubscribedSubcategories(page, limit).subscribe(result => subSubCatPage = result);

    const url = `${subscriptionsUrl}/subcategories?page=${page}&limit=${limit}`;
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmptySubscribedSubcategoriesPage);

    tick();

    expect(subSubCatPage).toBeDefined();

    expect(subSubCatPage.data).toBeDefined();
    expect(subSubCatPage.data.length).toEqual(mockEmptySubscribedSubcategoriesPage.data.length);

    expect(subSubCatPage.links).toBeDefined();
    expect(subSubCatPage.links.size).toEqual(subSubCatPage.links.size);
    expect(subSubCatPage.links.has('prev')).toBeTrue();
    expect(subSubCatPage.links.has('self')).toBeTrue();
    expect(subSubCatPage.links.has('next')).toBeFalse();
  }));

  it('getSubscribedSubcategories() should return error => invalid page number', fakeAsync(() => {
    const page = -1;
    const limit = 5;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Invalid page number',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.getSubscribedSubcategories(page, limit).subscribe(result => response = result);

    const url = `${subscriptionsUrl}/subcategories?page=${page}&limit=${limit}`;
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('getSubscribedSubcategories() should return error => invalid limit number', fakeAsync(() => {
    const page = 0;
    const limit = -5;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Invalid limit number',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.getSubscribedSubcategories(page, limit).subscribe(result => response = result);

    const url = `${subscriptionsUrl}/subcategories?page=${page}&limit=${limit}`;
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('getSubscribedOffers() should return some subscribed offers', fakeAsync(() => {
    const categoryId = 1;
    const subcategoryId = 1;
    const page = 0;
    const limit = 5;

    let subscribedOfferPage: SubscribedOfferPage;
    service.getSubscribedOffers(categoryId, subcategoryId, page, limit).subscribe(result => subscribedOfferPage = result);

    const url = `${subscriptionsUrl}?page=${page}&limit=${limit}&categoryId=${categoryId}&subcategoryId=${subcategoryId}`;
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubscribedOffersPage);

    tick();

    expect(subscribedOfferPage).toBeDefined();

    expect(subscribedOfferPage.data).toBeDefined();
    expect(subscribedOfferPage.data.length).toEqual(mockSubscribedSubcategoriesPage.data.length);

    expect(subscribedOfferPage.links).toBeDefined();
    expect(subscribedOfferPage.links.size).toEqual(mockSubscribedSubcategoriesPage.links.size);
    expect(subscribedOfferPage.links.has('self')).toBeTrue();
    expect(subscribedOfferPage.links.has('next')).toBeTrue();
    expect(subscribedOfferPage.links.has('prev')).toBeFalse();
  }));

  it('getSubscribedOffers() should not return any subscribed offers => empty page', fakeAsync(() => {
    const categoryId = 1;
    const subcategoryId = 1;
    const page = 1000;
    const limit = 5;

    let subscribedOfferPage: SubscribedOfferPage;
    service.getSubscribedOffers(categoryId, subcategoryId, page, limit).subscribe(result => subscribedOfferPage = result);

    const url = `${subscriptionsUrl}?page=${page}&limit=${limit}&categoryId=${categoryId}&subcategoryId=${subcategoryId}`;
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmptySubscribedOffersPage);

    tick();

    expect(subscribedOfferPage).toBeDefined();

    expect(subscribedOfferPage.data).toBeDefined();
    expect(subscribedOfferPage.data.length).toEqual(mockEmptySubscribedSubcategoriesPage.data.length);

    expect(subscribedOfferPage.links).toBeDefined();
    expect(subscribedOfferPage.links.size).toEqual(mockEmptySubscribedSubcategoriesPage.links.size);
    expect(subscribedOfferPage.links.has('prev')).toBeTrue();
    expect(subscribedOfferPage.links.has('self')).toBeTrue();
    expect(subscribedOfferPage.links.has('next')).toBeFalse();
  }));

  it('getSubscribedOffers() should return error => invalid page number', fakeAsync(() => {
    const categoryId = 1;
    const subcategoryId = 1;
    const page = -1;
    const limit = 5;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Invalid page number',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.getSubscribedOffers(categoryId, subcategoryId, page, limit).subscribe(result => response = result);

    const url = `${subscriptionsUrl}?page=${page}&limit=${limit}&categoryId=${categoryId}&subcategoryId=${subcategoryId}`;
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('getSubscribedOffers() should return error => invalid limit number', fakeAsync(() => {
    const categoryId = 1;
    const subcategoryId = 1;
    const page = 0;
    const limit = -5;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Invalid limit number',
      status: 400,
      statusText: 'Bad request'
    });

    let response: any;
    service.getSubscribedOffers(categoryId, subcategoryId, page, limit).subscribe(result => response = result);

    const url = `${subscriptionsUrl}?page=${page}&limit=${limit}&categoryId=${categoryId}&subcategoryId=${subcategoryId}`;
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));

  it('getSubscribedOffers() should return error => subcategory not found', fakeAsync(() => {
    const categoryId = 1000;
    const subcategoryId = 1;
    const page = 0;
    const limit = 5;

    const expectedResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'Subcategory not found',
      status: 404,
      statusText: 'Not found'
    });

    let response: any;
    service.getSubscribedOffers(categoryId, subcategoryId, page, limit).subscribe(result => response = result);

    const url = `${subscriptionsUrl}?page=${page}&limit=${limit}&categoryId=${categoryId}&subcategoryId=${subcategoryId}`;
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(expectedResponse);

    tick();

    expect(response.error).toEqual(expectedResponse.error);
    expect(response.status).toEqual(expectedResponse.status);
    expect(response.statusText).toEqual(expectedResponse.statusText);
  }));
});
