import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';

import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { CulturalOffersService } from './cultural-offers.service';
import { HttpClient } from '@angular/common/http';
import { CulturalOffer, CulturalOfferLocation, LocationRange } from './cultural-offer';
import { environment } from 'src/environments/environment';

describe('CulturalOffersService', () => {
  let service: CulturalOffersService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  
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

  it('getCulturalOffer() should return a culturalOffer', fakeAsync(() => {
    const mockCulturalOffer: CulturalOffer = {
      id: 1,
      name: 'Offer 1',
      description: 'Some description',
      images: ['1', '2'],
      latitude: 45.0,
      longitude: 45.0
    };

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
    const mockOfferLocations: CulturalOfferLocation[] = [
      {
        id: 1,
        name: 'Offer 1',
        location: {
          latitude: 40.0,
          longitude: 40.0,
          address: 'Address 1'
        }
      },
      {
        id: 2,
        name: 'Offer 2',
        location: {
          latitude: 41.0,
          longitude: 41.0,
          address: 'Address 2'
        }
      }
    ];

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
    const id: number = 1;

    let url = `${environment.apiUrl}/api/cultural-offers/${id}/subscriptions`;
    
    let success: boolean = false;
    service.subscribeToCulturalOffer(id).subscribe(() => success = true);

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('POST');
    req.flush(null);

    tick();

    expect(success).toEqual(true);
  }));

  it('unsubscribeFromCulturalOffer() should succeed', fakeAsync(() => {
    const id: number = 1;

    let url = `${environment.apiUrl}/api/cultural-offers/${id}/subscriptions`;
    
    let success: boolean = false;
    service.unsubscribeFromCulturalOffer(id).subscribe(() => success = true);

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);

    tick();

    expect(success).toEqual(true);
  }));

  it('getSubscribed() should return true', fakeAsync(() => {
    const id: number = 1;

    let subscribed: boolean;
    service.getSubscribed(id).subscribe((result) => subscribed = result);

    let url = `${environment.apiUrl}/api/cultural-offers/${id}/subscriptions`;

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush({}, {status: 204, statusText: 'No Content'});

    expect(subscribed).toEqual(true);
  }));

  it ('getSubscribed() should return false', fakeAsync(() => {
    const id: number = 1;

    let subscribed: boolean;
    service.getSubscribed(id).subscribe((result) => subscribed = result);

    let url = `${environment.apiUrl}/api/cultural-offers/${id}/subscriptions`;

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush({}, {status: 404, statusText: 'Not Found'});

    expect(subscribed).toEqual(false);
  }));
});
