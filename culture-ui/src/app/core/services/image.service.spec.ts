import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed, getTestBed, fakeAsync, tick } from '@angular/core/testing';
import { mockImagesToAdd, mockEmptyImagesToAdd } from 'src/app/shared/testing/mock-data';
import { environment } from 'src/environments/environment';

import { ImageService } from './image.service';

describe('ImageService', () => {
  let service: ImageService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  const imagesUrl = `${environment.apiUrl}/api/images`;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ImageService]
    });
    injector = getTestBed();
    service = TestBed.inject(ImageService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('addImages() should add images', fakeAsync(() => {
    let imagesIds: number[];
    service.addImages(mockImagesToAdd).subscribe(result => imagesIds = result);

    const req = httpMock.expectOne(imagesUrl);
    expect(req.request.method).toBe('POST');
    req.flush([1, 2]);

    tick();

    expect(imagesIds).toBeDefined();
    expect(imagesIds.length).toEqual(2);
  }));

  it('addImages() should not images', fakeAsync(() => {
    let imagesIds: number[];
    service.addImages(mockEmptyImagesToAdd).subscribe(result => imagesIds = result);

    const req = httpMock.expectOne(imagesUrl);
    expect(req.request.method).toBe('POST');
    req.flush([]);

    tick();

    expect(imagesIds).toBeDefined();
    expect(imagesIds.length).toEqual(0);
  }));
});
