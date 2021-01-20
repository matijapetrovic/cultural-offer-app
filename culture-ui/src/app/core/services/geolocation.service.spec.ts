import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed } from '@angular/core/testing';
import { LocationRange } from 'src/app/modules/cultural-offers/cultural-offer';
import { environment } from 'src/environments/environment';

import { GeolocationService } from './geolocation.service';

describe('GeolocationService', () => {
  let service: GeolocationService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [GeolocationService]
    });
    injector = getTestBed();
    service = TestBed.inject(GeolocationService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('geocode() should return valid location range', fakeAsync(() => {
    const location: string = 'Novi Sad Serbia';

    const mockLocation = {
      results: [
        {
          geometry: {
            viewport: {
              southwest: {
                lat: 45.0,
                lng: 45.0,
              },
              northeast: {
                lat: 50.0,
                lng: 50.0
              }
            }
          }
        }
      ]
    };

    let locationRange: LocationRange;
    service.geocode(location).subscribe((result) => locationRange = result);

    const address: string = location.replace(" ", "%20").replace(" ","%20");
    const url = `${service.geolocationApiUrl}?address=${address}&key=${environment.mapsApiKey}`;
    
    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockLocation);

    expect(locationRange).toBeDefined();
    expect(locationRange.latitudeFrom).toEqual(45.0);
    expect(locationRange.latitudeTo).toEqual(50.0);
    expect(locationRange.longitudeFrom).toEqual(45.0);
    expect(locationRange.longitudeTo).toEqual(50.0);
  }));
});
