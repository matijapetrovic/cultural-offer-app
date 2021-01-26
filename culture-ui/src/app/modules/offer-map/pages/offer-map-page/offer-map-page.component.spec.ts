import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { GeolocationService } from 'src/app/core/services/geolocation.service';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { Category } from 'src/app/modules/categories/category';
import { CulturalOfferLocationsFilter, LocationRange } from 'src/app/modules/cultural-offers/cultural-offer';
import { CulturalOffersService } from 'src/app/modules/cultural-offers/cultural-offers.service';
import { SubcategoriesService } from 'src/app/modules/subcategories/subcategories.service';
import { 
  invalidLocation, 
  invalidLocationMessage, 
  mockCategoryNames, 
  mockLocationRange, 
  mockOfferLocations, 
  mockSubcategoryNames, 
  validLocation } from 'src/app/shared/testing/mock-data';
import { OfferMapFilterFormComponent } from '../../components/offer-map-filter-form/offer-map-filter-form.component';
import { OfferMapSearchLocationComponent } from '../../components/offer-map-search-location/offer-map-search-location.component';
import { OfferMapComponent } from '../../components/offer-map/offer-map.component';

import { OfferMapPageComponent } from './offer-map-page.component';

describe('OfferMapPageComponent', () => {
  let component: OfferMapPageComponent;
  let fixture: ComponentFixture<OfferMapPageComponent>;

  let culturalOfferService: CulturalOffersService;
  let categoriesService: CategoriesService;
  let subcategoriesService: SubcategoriesService;
  let geolocationService: GeolocationService;
  let messageService: MessageService;

  beforeEach(async () => {
    const culturalOffersServiceMock = {
      getCulturalOfferLocations: jasmine.createSpy('getCulturalOfferLocations')
        .and.returnValue(of(mockOfferLocations))
    };

    const categoriesServiceMock = {
      getCategoryNames: jasmine.createSpy('getCategoryNames')
        .and.returnValue(of(mockCategoryNames))
    };

    const subcategoriesServiceMock = {
      getSubcategoryNames: jasmine.createSpy('getSubcategoryNames')
        .and.returnValue(of(mockSubcategoryNames))
    };

    const geolocationServiceMock = {
      geocode: jasmine.createSpy('geocode')
        .and.callFake((location: string) => {
          if (location === 'Location') {
            return of(mockLocationRange);
          }
          return throwError(new Error(invalidLocationMessage.detail));
        })
    };

    await TestBed.configureTestingModule({
      declarations: [ OfferMapPageComponent, OfferMapComponent, OfferMapFilterFormComponent, OfferMapSearchLocationComponent ],
      providers: [
        { provide: CulturalOffersService, useValue: culturalOffersServiceMock },
        { provide: CategoriesService, useValue: categoriesServiceMock },
        { provide: SubcategoriesService, useValue: subcategoriesServiceMock },
        { provide: GeolocationService, useValue: geolocationServiceMock }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferMapPageComponent);

    culturalOfferService = TestBed.inject(CulturalOffersService);
    categoriesService = TestBed.inject(CategoriesService);
    subcategoriesService = TestBed.inject(SubcategoriesService);
    geolocationService = TestBed.inject(GeolocationService);
    messageService = TestBed.inject(MessageService);

    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create and init', fakeAsync(() => {
    expect(component).toBeTruthy();

    tick();

    expect(culturalOfferService.getCulturalOfferLocations).toHaveBeenCalled();
    expect(categoriesService.getCategoryNames).toHaveBeenCalled();

    expect(component.culturalOffers.length).toEqual(mockOfferLocations.length);
    expect(component.categories.length).toEqual(mockCategoryNames.length);
  }));

  it('should have cultural offers after calling getCulturalOfferLocations()', fakeAsync(() => {
    component.culturalOffers = [];
    fixture.detectChanges();

    component.getCulturalOfferLocations();
    tick();

    expect(culturalOfferService.getCulturalOfferLocations).toHaveBeenCalled();
    expect(component.culturalOffers.length).toEqual(mockOfferLocations.length);
    expect(component.culturalOffers[0].id).toEqual(mockOfferLocations[0].id);
    expect(component.culturalOffers[0].name).toEqual(mockOfferLocations[0].name);
    expect(component.culturalOffers[0].location).toEqual(mockOfferLocations[0].location);
    expect(component.culturalOffers[1].id).toEqual(mockOfferLocations[1].id);
    expect(component.culturalOffers[1].name).toEqual(mockOfferLocations[1].name);
    expect(component.culturalOffers[1].location).toEqual(mockOfferLocations[1].location);
  }));

  it('should have categories after calling getCategories()', fakeAsync(() => {
    component.categories = [];
    fixture.detectChanges();

    component.getCategories();
    tick();

    expect(categoriesService.getCategoryNames).toHaveBeenCalled();
    expect(component.categories.length).toEqual(mockCategoryNames.length);
    expect(component.categories[0].id).toEqual(mockCategoryNames[0].id);
    expect(component.categories[0].name).toEqual(mockCategoryNames[0].name);
    expect(component.categories[1].id).toEqual(mockCategoryNames[1].id);
    expect(component.categories[1].name).toEqual(mockCategoryNames[1].name);
  }));

  it('should have subcategories when getSubcategories()', fakeAsync(() => {
    component.subcategories = [];
    fixture.detectChanges();

    const mockCategory: Category = {
      id: 1,
      name: 'Cat 1'
    };

    component.getSubcategories(mockCategory);
    tick();

    expect(subcategoriesService.getSubcategoryNames).toHaveBeenCalledWith(mockCategory.id);
    expect(component.subcategories.length).toEqual(mockSubcategoryNames.length);
    expect(component.subcategories[0].id).toEqual(mockSubcategoryNames[0].id);
    expect(component.subcategories[0].name).toEqual(mockSubcategoryNames[0].name);
    expect(component.subcategories[0].categoryId).toEqual(mockSubcategoryNames[0].categoryId);
    expect(component.subcategories[1].id).toEqual(mockSubcategoryNames[1].id);
    expect(component.subcategories[1].name).toEqual(mockSubcategoryNames[1].name);
    expect(component.subcategories[1].categoryId).toEqual(mockSubcategoryNames[1].categoryId);
  }));

  it('should update cultural offer locations when updateFilters()', fakeAsync(() => {
    const mockEvent: CulturalOfferLocationsFilter = {
      category: {
        id: 1,
        name: 'Cat 1'
      },
      subcategory: {
        id: 1,
        name: 'Subcat 1',
        categoryId: 1
      }
    };
    component.culturalOffers = [];
    fixture.detectChanges();
    component.updateFilters(mockEvent);
    tick();

    expect(culturalOfferService.getCulturalOfferLocations)
      .toHaveBeenCalledWith(jasmine.any(Object), mockEvent.category.id, mockEvent.subcategory.id);
    expect(component.culturalOffers.length).toEqual(mockOfferLocations.length);
  }));

  it('should update cultural offer locations when updateLocations()', fakeAsync(() => {
    component.culturalOffers = [];
    fixture.detectChanges();
    component.updateLocations(mockLocationRange);
    tick();

    expect(culturalOfferService.getCulturalOfferLocations).toHaveBeenCalledWith(mockLocationRange, null, null);
    expect(component.culturalOffers.length).toEqual(mockOfferLocations.length);
  }));

  it('should update cultural offer locations when resetFilters()', fakeAsync(() => {
    component.resetFilters();
    tick();

    expect(culturalOfferService.getCulturalOfferLocations).toHaveBeenCalledWith(jasmine.any(Object), null, null);
    expect(component.subcategories.length).toEqual(0);
    expect(component.culturalOffers.length).toEqual(mockOfferLocations.length);
  }));

  it('should search locations and update offers when searchLocation() with valid location', fakeAsync(() => {
    component.culturalOffers = [];
    fixture.detectChanges();
    component.searchLocation(validLocation);
    tick();

    expect(geolocationService.geocode).toHaveBeenCalledWith(validLocation);
    expect(culturalOfferService.getCulturalOfferLocations).toHaveBeenCalledWith(mockLocationRange, null, null);
    expect(component.culturalOffers.length).toEqual(mockOfferLocations.length);
  }));

  it('should search locations and display error when searchLocation() with invalid location', fakeAsync(() => {
    component.searchLocation(invalidLocation);
    tick();

    expect(geolocationService.geocode).toHaveBeenCalledWith(invalidLocation);
    expect(messageService.add).toHaveBeenCalledWith(invalidLocationMessage);
  }));
});
