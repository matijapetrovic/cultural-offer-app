import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { environment } from 'src/environments/environment';

import { SubcategoriesService } from './subcategories.service';
import { Subcategory, SubcategoriesPage } from './subcategory';

describe('SubcategoriesService', () => {
  let service: SubcategoriesService;
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  const errorHandler = jasmine.createSpyObj('errorHandler', ['handleError']);

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


  // GET SUBCATEGORIES
  it('getSubcategories() should return valid categories', fakeAsync(() => {
    let subcategoriesPage: SubcategoriesPage;

    const page = 0;
    const limit = 3;
    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;

    const mockSubcategoriesPage = {
      data: [
        { id: 1, categoryId, name: 'Subateogory1' },
        { id: 2, categoryId, name: 'Subcateogory2' },
        { id: 3, categoryId, name: 'Subcateogory3' },
      ]
    };

    service.getSubcategories(categoryId, page, limit).subscribe(data => { subcategoriesPage = data; });

    const req = httpMock.expectOne(subcategoriesUrl + `?page=${page}&limit=${limit}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubcategoriesPage);

    tick();

    expect(subcategoriesPage.data.length).toEqual(3, 'should contain given amount of subcategories in subcategory page data');
    expect(subcategoriesPage.data[0].id).toEqual(mockSubcategoriesPage.data[0].id);
    expect(subcategoriesPage.data[0].categoryId).toEqual(mockSubcategoriesPage.data[0].categoryId);
    expect(subcategoriesPage.data[0].name).toEqual(mockSubcategoriesPage.data[0].name);
    expect(subcategoriesPage.data[1].id).toEqual(mockSubcategoriesPage.data[1].id);
    expect(subcategoriesPage.data[1].categoryId).toEqual(mockSubcategoriesPage.data[1].categoryId);
    expect(subcategoriesPage.data[1].name).toEqual(mockSubcategoriesPage.data[1].name);
    expect(subcategoriesPage.data[2].id).toEqual(mockSubcategoriesPage.data[2].id);
    expect(subcategoriesPage.data[2].categoryId).toEqual(mockSubcategoriesPage.data[2].categoryId);
    expect(subcategoriesPage.data[2].name).toEqual(mockSubcategoriesPage.data[2].name);
  }));

  it('getSubategories() should return empty Categories', fakeAsync(() => {
    let subcategoriesPage: SubcategoriesPage;

    const page = 0;
    const limit = 3;
    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;

    const mockSubcategoriesPage = {
      data: [
      ]
    };

    service.getSubcategories(categoryId, page, limit).subscribe(data => { subcategoriesPage = data; });

    const req = httpMock.expectOne(subcategoriesUrl + `?page=${page}&limit=${limit}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubcategoriesPage);

    tick();

    expect(subcategoriesPage.data.length).toEqual(0, 'should not contain subcategories in subcategory page data');
  }));

  it('getSubategories() should throw category does not exist', fakeAsync(() => {
    let subcategoriesPage: SubcategoriesPage;

    const page = 0;
    const limit = 3;
    const categoryId = -1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;
    const errorMessage = 'Category does not exist!';

    const mockSubcategoriesPage = {
      data: [
      ]
    };

    service.getSubcategories(categoryId, page, limit).subscribe(data => { subcategoriesPage = data; });

    const req = httpMock.expectOne(subcategoriesUrl + `?page=${page}&limit=${limit}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubcategoriesPage);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));


  // ADD SUBCATEGORY

  it('addSubcategory() should add valid Subategory', fakeAsync(() => {
    let response: any;
    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;

    const mockSubcategory: Subcategory = {
      id: null,
      name: 'Category',
      categoryId
    };
    service.addSubcategory(mockSubcategory).subscribe(data => { response = data; });

    const req = httpMock.expectOne(subcategoriesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(mockSubcategory);

    tick();

    expect(response).toBeTruthy();
  }));

  it('addSubcategory() should throw subcategiory already exists', fakeAsync(() => {
    let response: any;
    const errorMessage = 'Subcategory already exists!';
    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;

    const mockSubcategory: Subcategory = {
      id: null,
      name: 'Category',
      categoryId
    };
    service.addSubcategory(mockSubcategory).subscribe(data => { response = data; });

    const req = httpMock.expectOne(subcategoriesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(mockSubcategory);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  it('addSubcategory() should throw category does not exist', fakeAsync(() => {

    let response: any;
    const errorMessage = 'Category does not exist!';
    const categoryId = -1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;

    const mockSubcategory: Subcategory = {
      id: null,
      name: 'Category',
      categoryId
    };
    service.addSubcategory(mockSubcategory).subscribe(data => { response = data; });

    const req = httpMock.expectOne(subcategoriesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(mockSubcategory);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  // UPDATE SUBCATEGOY

  it('updateCategory() should update valid Category', fakeAsync(() => {
    let subcategory: Subcategory;
    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;

    const updatedMockSubcategory: Subcategory = {
      id: 1,
      name: 'Updated Category',
      categoryId
    };

    service.updateSubcategory(updatedMockSubcategory).subscribe();

    const req = httpMock.expectOne(subcategoriesUrl + `/${updatedMockSubcategory.id}`);
    expect(req.request.method).toBe('PUT');
    req.flush(updatedMockSubcategory);

    tick();

    service.getSubcategory(updatedMockSubcategory).subscribe(data => subcategory = data);
    const reqUpdate = httpMock.expectOne(subcategoriesUrl + `/${updatedMockSubcategory.id}`);
    expect(reqUpdate.request.method).toBe('GET');
    reqUpdate.flush(updatedMockSubcategory);

    tick();

    expect(subcategory).toBeTruthy();
    expect(subcategory.id).toEqual(updatedMockSubcategory.id);
    expect(subcategory.name).toEqual(updatedMockSubcategory.name);
    expect(subcategory.categoryId).toEqual(updatedMockSubcategory.categoryId);
  }));

  it('updateCategory() should throw invalid Subcategory Id', fakeAsync(() => {
    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;
    const errorMessage = 'Invalid Subcategory Id!';

    const updatedMockSubcategory: Subcategory = {
      id: -1,
      name: 'Updated Category',
      categoryId
    };

    service.updateSubcategory(updatedMockSubcategory).subscribe();

    const req = httpMock.expectOne(subcategoriesUrl + `/${updatedMockSubcategory.id}`);
    expect(req.request.method).toBe('PUT');
    req.flush(updatedMockSubcategory);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  it('updateCategory() should throw invalid Category Id', fakeAsync(() => {
    const categoryId = -1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;
    const errorMessage = 'Invalid Category Id!';

    const updatedMockSubcategory: Subcategory = {
      id: 1,
      name: 'Updated Category',
      categoryId
    };

    service.updateSubcategory(updatedMockSubcategory).subscribe();

    const req = httpMock.expectOne(subcategoriesUrl + `/${updatedMockSubcategory.id}`);
    expect(req.request.method).toBe('PUT');
    req.flush(updatedMockSubcategory);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  // GET SUBCATEGORY

  it('getSubcategory() should return valid subcategory', fakeAsync(() => {

    let subcategory: Subcategory;
    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;

    const getMockSubcategory: Subcategory = {
      id: 1,
      name: 'Get Category',
      categoryId
    };

    service.getSubcategory(getMockSubcategory).subscribe(response => { subcategory = response; });

    const req = httpMock.expectOne(subcategoriesUrl + `/${getMockSubcategory.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(getMockSubcategory);

    tick();

    expect(subcategory).toBeTruthy();
    expect(subcategory.id).toEqual(getMockSubcategory.id);
    expect(subcategory.name).toEqual(getMockSubcategory.name);
    expect(subcategory.categoryId).toEqual(getMockSubcategory.categoryId);
  }));

  it('getSubcategory() should throw Invalid Subcategory Id', fakeAsync(() => {

    let subcategory: Subcategory;
    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;
    const errorMessage = 'Invalid Subcategory Id!';

    const getMockSubcategory: Subcategory = {
      id: -1,
      name: 'Get Category',
      categoryId
    };

    service.getSubcategory(getMockSubcategory).subscribe(response => { subcategory = response; });

    const req = httpMock.expectOne(subcategoriesUrl + `/${getMockSubcategory.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(getMockSubcategory);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  it('getSubcategory() should throw Invalid Category Id', fakeAsync(() => {

    let subcategory: Subcategory;
    const categoryId = -1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;
    const errorMessage = 'Invalid Subcategory Id!';

    const getMockSubcategory: Subcategory = {
      id: 1,
      name: 'Get Category',
      categoryId
    };

    service.getSubcategory(getMockSubcategory).subscribe(response => { subcategory = response; });

    const req = httpMock.expectOne(subcategoriesUrl + `/${getMockSubcategory.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(getMockSubcategory);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  // DELETE SUBCATEGORY

  it('deleteSubcategory() should delete valid subcategory', fakeAsync(() => {

    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;

    const deleteMockSubcategory: Subcategory = {
      id: 1,
      name: 'Delete Category',
      categoryId
    };

    service.deleteSubcategory(deleteMockSubcategory).subscribe();
    const req = httpMock.expectOne(subcategoriesUrl + `/${deleteMockSubcategory.id}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));

  it('deleteSubcategory() should throw invalid Subcategory Id', fakeAsync(() => {

    const categoryId = 1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;
    const errorMessage = 'Invalid Subcategory Id!';

    const deleteMockSubcategory: Subcategory = {
      id: -1,
      name: 'Delete Category',
      categoryId
    };

    service.deleteSubcategory(deleteMockSubcategory).subscribe();
    const req = httpMock.expectOne(subcategoriesUrl + `/${deleteMockSubcategory.id}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  it('deleteSubcategory() should throw invalid Category Id', fakeAsync(() => {

    const categoryId = -1;
    const subcategoriesUrl = `${environment.apiUrl}/api/categories/${categoryId}/subcategories`;
    const errorMessage = 'Invalid Category Id!';

    const deleteMockSubcategory: Subcategory = {
      id: 1,
      name: 'Delete Category',
      categoryId
    };

    service.deleteSubcategory(deleteMockSubcategory).subscribe();
    const req = httpMock.expectOne(subcategoriesUrl + `/${deleteMockSubcategory.id}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));
});
