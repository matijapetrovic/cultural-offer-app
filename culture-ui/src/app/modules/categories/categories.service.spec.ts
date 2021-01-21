import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { fakeAsync, tick } from '@angular/core/testing';
import { CategoriesService } from './categories.service';
import { Category, CategoriesPage } from './../categories/category';
import {} from 'jasmine';
import { environment } from 'src/environments/environment';

describe('CategoriesService', () => {
  let injector;
  let service: CategoriesService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  const errorHandler = jasmine.createSpyObj('errorHandler', ['handleError']);
  const categoriesUrl = `${environment.apiUrl}/api/categories`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CategoriesService]
    });

    injector = getTestBed();
    service = TestBed.inject(CategoriesService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getCategory() should return valid Category', fakeAsync(() => {
    let category: Category;
    const mockCategory: Category = {
      id: 1,
      name: 'Category'
    };
    service.getCategory(mockCategory.id).subscribe(data => { category = data; });

    const req = httpMock.expectOne(categoriesUrl + `/${mockCategory.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCategory);

    tick();

    expect(category).toBeTruthy();
    expect(category.id).toEqual(mockCategory.id);
    expect(category.name).toEqual(mockCategory.name);
  }));

  it('getCategory() should throw exception', fakeAsync(() => {
    let category: Category;
    const id = -1;
    const errorMessage = 'Category does not exist';

    service.getCategory(id).subscribe(data => { category = data; });

    const req = httpMock.expectOne(categoriesUrl + `/${id}`);
    expect(req.request.method).toBe('GET');
    req.flush(null);

    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(category).toBeNull();
    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  it('getCategoryNames() should return valid Category names', fakeAsync(() => {
    let categoryNames: Category[];
    const mockCategoryNames = [
      { id: 1, name: 'Cateogory1' },
      { id: 2, name: 'Cateogory2' },
      { id: 3, name: 'Cateogory3' },
    ];

    service.getCategoryNames().subscribe(data => { categoryNames = data; });

    const req = httpMock.expectOne(categoriesUrl + `/names`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCategoryNames);

    tick();

    expect(categoryNames.length).toEqual(3, 'should contain given amount of category names');
    expect(categoryNames[0].id).toEqual(mockCategoryNames[0].id);
    expect(categoryNames[0].name).toEqual(mockCategoryNames[0].name);
    expect(categoryNames[1].id).toEqual(mockCategoryNames[1].id);
    expect(categoryNames[1].name).toEqual(mockCategoryNames[1].name);
    expect(categoryNames[2].id).toEqual(mockCategoryNames[2].id);
    expect(categoryNames[2].name).toEqual(mockCategoryNames[2].name);
  }));

  it('getCategoryNames() should return empty Category names', fakeAsync(() => {
    let categoryNames: Category[];
    const mockCategoryNames = [
    ];

    service.getCategoryNames().subscribe(data => { categoryNames = data; });

    const req = httpMock.expectOne(categoriesUrl + `/names`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCategoryNames);

    tick();

    expect(categoryNames.length).toEqual(0, 'should not contain category names');

  }));

  it('getCategories() should return valid Categories', fakeAsync(() => {
    let categoriesPage: CategoriesPage;
    const page = 0;
    const limit = 3;
    const mockCategoriesPage = {
      data: [
        { id: 1, name: 'Cateogory1' },
        { id: 2, name: 'Cateogory2' },
        { id: 3, name: 'Cateogory3' },
      ]
    };

    service.getCategories(page, limit).subscribe(data => { categoriesPage = data; });

    const req = httpMock.expectOne(categoriesUrl + `?page=${page}&limit=${limit}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCategoriesPage);

    tick();

    expect(categoriesPage.data.length).toEqual(3, 'should contain given amount of categories in category page data');
    expect(categoriesPage.data[0].id).toEqual(mockCategoriesPage.data[0].id);
    expect(categoriesPage.data[0].name).toEqual(mockCategoriesPage.data[0].name);
    expect(categoriesPage.data[1].id).toEqual(mockCategoriesPage.data[1].id);
    expect(categoriesPage.data[1].name).toEqual(mockCategoriesPage.data[1].name);
    expect(categoriesPage.data[2].id).toEqual(mockCategoriesPage.data[2].id);
    expect(categoriesPage.data[2].name).toEqual(mockCategoriesPage.data[2].name);
  }));


  it('getCategories() should empty Categories', fakeAsync(() => {
    let categoriesPage: CategoriesPage;
    const page = 0;
    const limit = 3;
    const mockCategoriesPage = {
      data: [
      ]
    };

    service.getCategories(page, limit).subscribe(data => { categoriesPage = data; });

    const req = httpMock.expectOne(categoriesUrl + `?page=${page}&limit=${limit}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCategoriesPage);

    tick();

    expect(categoriesPage.data.length).toEqual(0, 'should not contain categories in category page data');
  }));



  it('addCategory() should add valid Category', fakeAsync(() => {
    let response: any;
    const mockCategory: Category = {
      id: null,
      name: 'Category'
    };
    service.addCategory(mockCategory).subscribe(data => { response = data; });

    const req = httpMock.expectOne(categoriesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(mockCategory);

    tick();

    expect(response).toBeTruthy();
  }));

  it('addCategory() should throw categiory already exists', fakeAsync(() => {
    let response: any;
    const errorMessage = 'Category already exists!';
    const mockCategory: Category = {
      id: null,
      name: 'Category'
    };
    service.addCategory(mockCategory).subscribe(data => { response = data; });

    const req = httpMock.expectOne(categoriesUrl);
    expect(req.request.method).toBe('POST');
    req.flush(mockCategory);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  it('updateCategory() should update valid Category', fakeAsync(() => {
    let category: Category;
    const updatedMockCategory: Category = {
      id: 1,
      name: 'Updated Category'
    };

    service.updateCategory(updatedMockCategory).subscribe();

    const req = httpMock.expectOne(categoriesUrl + `/${updatedMockCategory.id}`);
    expect(req.request.method).toBe('PUT');
    req.flush(updatedMockCategory);

    tick();

    service.getCategory(updatedMockCategory.id).subscribe(data => category = data);
    const reqUpdate = httpMock.expectOne(categoriesUrl + `/${updatedMockCategory.id}`);
    expect(reqUpdate.request.method).toBe('GET');
    reqUpdate.flush(updatedMockCategory);

    tick();

    expect(category).toBeTruthy();
    expect(category.id).toEqual(updatedMockCategory.id);
    expect(category.name).toEqual(updatedMockCategory.name);

  }));

  it('updateCategory() should throw category already exists', fakeAsync(() => {
    const errorMessage = 'Category already exists!';
    const updatedMockCategory: Category = {
      id: 1,
      name: 'Updated Category'
    };

    service.updateCategory(updatedMockCategory).subscribe();

    const req = httpMock.expectOne(categoriesUrl + `/${updatedMockCategory.id}`);
    expect(req.request.method).toBe('PUT');
    req.flush(updatedMockCategory);
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));

  it('deleteCategory() should delete valid Category', fakeAsync(() => {
    const id = 1;

    service.deleteCategory(id).subscribe();
    const req = httpMock.expectOne(categoriesUrl + `/${id}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));

  it('deleteCategory() should throw category does not exist', fakeAsync(() => {
    const id = -1;
    const errorMessage = 'Category does not exist!';

    service.deleteCategory(id).subscribe();
    const req = httpMock.expectOne(categoriesUrl + `/${id}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
    errorHandler.handleError.and.callFake(() => {
      throw errorMessage;
    });

    tick();

    expect(errorHandler.handleError).toThrow(errorMessage);
  }));
});
