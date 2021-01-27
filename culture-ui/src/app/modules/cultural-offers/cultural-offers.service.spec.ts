import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';

import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { CulturalOffersService } from './cultural-offers.service';
import { HttpClient } from '@angular/common/http';
import { CulturalOffer, CulturalOfferLocation, CulturalOffersPage, CulturalOfferToAdd, LocationRange } from './cultural-offer';
import { environment } from 'src/environments/environment';
import { mockCulturalOffer, mockCulturalOffersPage, mockCulturalOfferToAdd, mockCulturalOfferView, mockOfferLocations } from 'src/app/shared/testing/mock-data';

describe('CulturalOffersService', () => {
  let service: CulturalOffersService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  let offerssUrl = `${environment.apiUrl}/api/cultural-offers`;
  const errorHandler = jasmine.createSpyObj('errorHandler', ['handleError']);

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CulturalOffersService]
    });
    injector = getTestBed();
    service = TestBed.inject(CulturalOffersService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // GET CULTURAL OFFERS
 it('getCulturaOffers() should return valid offers', fakeAsync(() => {
  let culturalOffersPage: CulturalOffersPage;
  
  const page = 0;
  const limit = 3;

  service.getCulturaOffers(page, limit).subscribe(data => { culturalOffersPage = data; });

  const req = httpMock.expectOne(offerssUrl + `?page=${page}&limit=${limit}`);
  expect(req.request.method).toBe('GET');
  req.flush(mockCulturalOffersPage);

  tick();

  expect(culturalOffersPage.data.length).toEqual(3, 'should contain given amount of offers in offers page data');

  expect(culturalOffersPage.data[0].id).toEqual(mockCulturalOffersPage.data[0].id);
  expect(culturalOffersPage.data[0].name).toEqual(mockCulturalOffersPage.data[0].name);
  expect(culturalOffersPage.data[0].description).toEqual(mockCulturalOffersPage.data[0].description);
  expect(culturalOffersPage.data[0].subcategory.id).toEqual(mockCulturalOffersPage.data[0].subcategory.id);
  expect(culturalOffersPage.data[0].subcategory.categoryId).toEqual(mockCulturalOffersPage.data[0].subcategory.categoryId);
  expect(culturalOffersPage.data[0].address).toEqual(mockCulturalOffersPage.data[0].address);

  expect(culturalOffersPage.data[1].id).toEqual(mockCulturalOffersPage.data[1].id);
  expect(culturalOffersPage.data[1].name).toEqual(mockCulturalOffersPage.data[1].name);
  expect(culturalOffersPage.data[1].description).toEqual(mockCulturalOffersPage.data[1].description);
  expect(culturalOffersPage.data[1].subcategory.id).toEqual(mockCulturalOffersPage.data[1].subcategory.id);
  expect(culturalOffersPage.data[1].subcategory.categoryId).toEqual(mockCulturalOffersPage.data[1].subcategory.categoryId);
  expect(culturalOffersPage.data[1].address).toEqual(mockCulturalOffersPage.data[1].address);

  expect(culturalOffersPage.data[2].id).toEqual(mockCulturalOffersPage.data[2].id);
  expect(culturalOffersPage.data[2].name).toEqual(mockCulturalOffersPage.data[2].name);
  expect(culturalOffersPage.data[2].description).toEqual(mockCulturalOffersPage.data[2].description);
  expect(culturalOffersPage.data[2].subcategory.id).toEqual(mockCulturalOffersPage.data[2].subcategory.id);
  expect(culturalOffersPage.data[2].subcategory.categoryId).toEqual(mockCulturalOffersPage.data[2].subcategory.categoryId);
  expect(culturalOffersPage.data[2].address).toEqual(mockCulturalOffersPage.data[2].address);
}));

it('getCulturaOffers() should return empty', fakeAsync(() => {
  let culturalOffersPage: CulturalOffersPage;
  
  const page = 0;
  const limit = 3;

  const mockOffersPage = {
    data: [
    ]
  };

  service.getCulturaOffers(page, limit).subscribe(data => { culturalOffersPage = data; });

  const req = httpMock.expectOne(offerssUrl + `?page=${page}&limit=${limit}`);
  expect(req.request.method).toBe('GET');
  req.flush(mockOffersPage);

  tick();

  expect(culturalOffersPage.data.length).toEqual(0, 'should not contain subcategories in subcategory page data');
}));


// ADD CULTURAL OFFER

it('addSubcategory() should add valid Subategory', fakeAsync(() => {
  let response: any;

  service.addCulturalOffer(mockCulturalOfferToAdd).subscribe(data => { response = data; });

  const req = httpMock.expectOne(offerssUrl);
  expect(req.request.method).toBe('POST');
  req.flush(mockCulturalOfferView);

  tick();

  expect(response).toBeTruthy();
}));

it('addSubcategory() should throw offer already exists', fakeAsync(() => {
  let response: any;
  const errorMessage = 'Offer already exists!';

  service.addCulturalOffer(mockCulturalOfferToAdd).subscribe(data => { response = data; });

  const req = httpMock.expectOne(offerssUrl);
  expect(req.request.method).toBe('POST');
  req.flush(mockCulturalOfferView);
  errorHandler.handleError.and.callFake(() => {
    throw errorMessage;
  });

  tick();

  expect(errorHandler.handleError).toThrow(errorMessage);
}));

// UPDATE SUBCATEGOY

it('updateCulturalOffer() should update valid offer', fakeAsync(() => {
  let offer: CulturalOffer;

  service.updateCulturalOffer(mockCulturalOfferToAdd).subscribe();

  const req = httpMock.expectOne(offerssUrl + `/${mockCulturalOfferToAdd.id}`);
  expect(req.request.method).toBe('PUT');
  req.flush(mockCulturalOfferView);

  tick();

  service.getCulturalOffer(mockCulturalOfferToAdd.id).subscribe(data => offer = data);
  const reqUpdate = httpMock.expectOne(offerssUrl + `/${mockCulturalOfferToAdd.id}`);
  expect(reqUpdate.request.method).toBe('GET');
  reqUpdate.flush(mockCulturalOffer);

  tick();

  expect(offer).toBeTruthy();
  expect(offer.id).toEqual(mockCulturalOfferToAdd.id);
  expect(offer.name).toEqual(mockCulturalOfferToAdd.name);
  expect(offer.description).toEqual(mockCulturalOfferToAdd.description);
}));

it('updateCulturalOffer() should throw invalid offer Id', fakeAsync(() => {
  let offer: CulturalOffer;
  const errorMessage = "Invalid Offer Id";

  const updatedMockOffer: CulturalOfferToAdd = {
    id: -1,
    name: "name",
    description: "description",
    latitude: 1.0,
    longitude: 1.0,
    images: [1, 2], 
    subcategoryId: 1,
    categoryId: 1,
    address: "address"
  };

  service.updateCulturalOffer(updatedMockOffer).subscribe();

  const req = httpMock.expectOne(offerssUrl + `/${updatedMockOffer.id}`);
  expect(req.request.method).toBe('PUT');
  req.flush(mockCulturalOfferView);
  errorHandler.handleError.and.callFake(() => {
    throw errorMessage;
  });

  tick();

  expect(errorHandler.handleError).toThrow(errorMessage);
}));

// DELETE OFFER

it('deleteCulturalOffer() should delete valid offer', fakeAsync(() => {

  service.deleteCulturalOffer(mockCulturalOfferToAdd).subscribe();
  const req = httpMock.expectOne(offerssUrl + `/${mockCulturalOfferToAdd.id}`);
  expect(req.request.method).toBe('DELETE');
  req.flush({});
}));

it('deleteCulturalOffer() should throw invalid offer Id', fakeAsync(() => {

    const errorMessage = "Invalid Offer Id";

    const deleteMockOffer: CulturalOfferToAdd = {
      id: -1,
      name: "name",
      description: "description",
      latitude: 1.0,
      longitude: 1.0,
      images: [1, 2], 
      subcategoryId: 1,
      categoryId: 1,
      address: "address"
    };

    service.deleteCulturalOffer(deleteMockOffer).subscribe();
    const req = httpMock.expectOne(offerssUrl + `/${deleteMockOffer.id}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));
  /////////

  it('getCulturalOffer() should return a culturalOffer', fakeAsync(() => {
    let culturalOffer: CulturalOffer;
    service.getCulturalOffer(mockCulturalOffer.id).subscribe(offer => culturalOffer = offer);

    const req = httpMock.expectOne(`${environment.apiUrl}/api/cultural-offers/${mockCulturalOffer.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCulturalOffer);

    tick();

    expect(culturalOffer).toBeDefined();
    expect(culturalOffer.id).toEqual(mockCulturalOffer.id);
    expect(culturalOffer.name).toEqual(mockCulturalOffer.name);
    expect(culturalOffer.description).toEqual(mockCulturalOffer.description);
    expect(culturalOffer.images.length).toEqual(mockCulturalOffer.images.length);
    expect(culturalOffer.latitude).toEqual(mockCulturalOffer.latitude);
    expect(culturalOffer.longitude).toEqual(mockCulturalOffer.longitude);
  }));

  it('getCulturalOfferLocations() should return some locations', fakeAsync(() => {
    const locationRange: LocationRange = {
      latitudeFrom: 39.0,
      longitudeFrom: 39.0,
      latitudeTo: 45.0,
      longitudeTo: 45.0
    };

    let culturalOfferLocations: CulturalOfferLocation[];
    service.getCulturalOfferLocations(locationRange, null, null).subscribe(locations => {
      culturalOfferLocations = locations;
    });

    let url = `${environment.apiUrl}/api/cultural-offers/locations`;
    url += `?latitudeFrom=${locationRange.latitudeFrom}&latitudeTo=${locationRange.latitudeTo}`;
    url += `&longitudeFrom=${locationRange.longitudeFrom}&longitudeTo=${locationRange.longitudeTo}`;

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockOfferLocations);

    tick();

    expect(culturalOfferLocations.length).toEqual(mockOfferLocations.length, 'should contain given amount of locations');

    expect(culturalOfferLocations[0].id).toEqual(mockOfferLocations[0].id);
    expect(culturalOfferLocations[0].name).toEqual(mockOfferLocations[0].name);
    expect(culturalOfferLocations[0].location.latitude).toEqual(mockOfferLocations[0].location.latitude);
    expect(culturalOfferLocations[0].location.longitude).toEqual(mockOfferLocations[0].location.longitude);
    expect(culturalOfferLocations[0].location.address).toEqual(mockOfferLocations[0].location.address);
    expect(culturalOfferLocations[1].id).toEqual(mockOfferLocations[1].id);
    expect(culturalOfferLocations[1].name).toEqual(mockOfferLocations[1].name);
    expect(culturalOfferLocations[1].location.latitude).toEqual(mockOfferLocations[1].location.latitude);
    expect(culturalOfferLocations[1].location.longitude).toEqual(mockOfferLocations[1].location.longitude);
    expect(culturalOfferLocations[1].location.address).toEqual(mockOfferLocations[1].location.address);
  }));

  it('subscribeToCulturalOffer() should succeed', fakeAsync(() => {
    const id = 1;

    const url = `${environment.apiUrl}/api/cultural-offers/${id}/subscriptions`;

    let success = false;
    service.subscribeToCulturalOffer(id).subscribe(() => success = true);

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('POST');
    req.flush(null);

    tick();

    expect(success).toEqual(true);
  }));

  it('unsubscribeFromCulturalOffer() should succeed', fakeAsync(() => {
    const id = 1;

    const url = `${environment.apiUrl}/api/cultural-offers/${id}/subscriptions`;

    let success = false;
    service.unsubscribeFromCulturalOffer(id).subscribe(() => success = true);

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);

    tick();

    expect(success).toEqual(true);
  }));

  it('getSubscribed() should return true', fakeAsync(() => {
    const id = 1;

    let subscribed: boolean;
    service.getSubscribed(id).subscribe((result) => subscribed = result);

    const url = `${environment.apiUrl}/api/cultural-offers/${id}/subscriptions`;

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush({}, {status: 204, statusText: 'No Content'});

    expect(subscribed).toEqual(true);
  }));

  it ('getSubscribed() should return false', fakeAsync(() => {
    const id = 1;

    let subscribed: boolean;
    service.getSubscribed(id).subscribe((result) => subscribed = result);

    const url = `${environment.apiUrl}/api/cultural-offers/${id}/subscriptions`;

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush({}, {status: 404, statusText: 'Not Found'});

    expect(subscribed).toEqual(false);
  }));
});
