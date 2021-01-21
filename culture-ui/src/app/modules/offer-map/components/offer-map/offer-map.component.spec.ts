import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { LocationRange } from 'src/app/modules/cultural-offers/cultural-offer';
import { mockCulturalOffer, mockLocationRange, mockMapBounds, mockOfferLocations } from 'src/app/shared/testing/mock-data';

import { OfferMapComponent } from './offer-map.component';

describe('OfferMapComponent', () => {
  let component: OfferMapComponent;
  let fixture: ComponentFixture<OfferMapComponent>;
  let map: google.maps.Map;
  
  beforeEach(async () => {
    const mockLatLng = {
      lat: jasmine.createSpy('lat')
        .and.returnValue(of(30.0)),
      lng: jasmine.createSpy('lng')
        .and.returnValue(of(30.0))
    };
    const mockBounds = {
      getNorthEast: jasmine.createSpy('getNorthEast')
        .and.returnValue(of(mockLatLng)),
      getSouthWest: jasmine.createSpy('getSouthWest')
        .and.returnValue(of(mockLatLng)),
      equals: jasmine.createSpy('equals')
        .and.callFake((other: google.maps.LatLngBounds) => false),
    };
    const mockMap = {
      getBounds: jasmine.createSpy('getBounds')
        .and.returnValue(of(mockBounds)),
      fitBounds: jasmine.createSpy('fitBounds')
    };
    await TestBed.configureTestingModule({  
      declarations: [ OfferMapComponent ],
      providers: [
        {provide: google.maps.Map, useValue: mockMap},
        {provide: google.maps.LatLngBounds, useValue: mockBounds},
        {provide: google.maps.LatLng, useValue: mockLatLng}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferMapComponent);
    map = TestBed.inject(google.maps.Map);
    component = fixture.componentInstance;
    component.culturalOffers = mockOfferLocations;
    component.bounds = mockLocationRange;
    fixture.detectChanges();
    component.setMap({map: map});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update map markers on locations update', () => {
    component.mapOverlays = [];

    component.updateCulturalOfferLocations();
    expect(component.mapOverlays.length).toEqual(component.culturalOffers.length);
  });

  it('should emit onBoundsChanged when map bounds changed', () => {
    let locationRange: LocationRange;
    component.onMapBoundsChanged.subscribe((event: LocationRange) => locationRange = event);

    component.mapBoundsChanged();

    expect(locationRange).toBeDefined();
  });

  it('should update cultural offer locations when input is changed ', () => {
    component.culturalOffers = null;
    component.culturalOffers = mockOfferLocations;
    fixture.detectChanges();

    expect(component.mapOverlays.length).toEqual(component.culturalOffers.length);
  });
}); 
