import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { environment } from 'src/environments/environment';

import { SubcategoriesService } from './subcategories.service';
import { Subcategory } from './subcategory';

describe('SubcategoriesService', () => {
  let service: SubcategoriesService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SubcategoriesService]
    });
    injector = getTestBed();
    service = TestBed.inject(SubcategoriesService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getSubcategoryNames() should return some names', fakeAsync(() => {
    const categoryId = 1;

    const mockSubcategories: Subcategory[] = [
      {
        id: 1,
        categoryId,
        name: 'Subacategory 1'
      },
      {
        id: 2,
        categoryId,
        name: 'Subcategory 2'
      }
    ];

    let subcategories: Subcategory[];
    service.getSubcategoryNames(categoryId).subscribe((result) => subcategories = result);

    const url = `${environment.apiUrl}/api/categories/${categoryId}/subcategories/names`;

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubcategories);

    tick();

    expect(subcategories.length).toEqual(mockSubcategories.length);
    expect(subcategories[0].id).toEqual(mockSubcategories[0].id);
    expect(subcategories[0].name).toEqual(mockSubcategories[0].name);
    expect(subcategories[0].categoryId).toEqual(mockSubcategories[0].categoryId);

    expect(subcategories[1].id).toEqual(mockSubcategories[1].id);
    expect(subcategories[1].name).toEqual(mockSubcategories[1].name);
    expect(subcategories[1].categoryId).toEqual(mockSubcategories[1].categoryId);

  }));
});
