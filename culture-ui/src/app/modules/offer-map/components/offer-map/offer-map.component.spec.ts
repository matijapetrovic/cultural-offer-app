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
    const mockMap = {
      getBounds: jasmine.createSpy('getBounds')
        .and.returnValue(mockMapBounds),
      fitBounds: jasmine.createSpy('fitBounds')
    };
    await TestBed.configureTestingModule({
      declarations: [ OfferMapComponent ],
      providers: [
        {provide: google.maps.Map, useValue: mockMap}
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
    component.setMap({map});
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
    component.mapBoundsChanged.subscribe((event: LocationRange) => locationRange = event);

    component.onMapBoundsChanged();

    expect(locationRange).toBeDefined();
  });

  it('should update cultural offer locations when input is changed ', () => {
    component.culturalOffers = null;
    fixture.detectChanges();
    component.culturalOffers = mockOfferLocations;
    fixture.detectChanges();

    expect(component.mapOverlays.length).toEqual(component.culturalOffers.length);
  });

  it('should update bounds when input is changed', () => {
    const newLocationRange: LocationRange = {
      latitudeFrom: 40.0,
      latitudeTo: 40.0,
      longitudeFrom: 40.0,
      longitudeTo: 40.0
    };

    component.bounds = newLocationRange;
    fixture.detectChanges();
    expect(component.map.fitBounds).toHaveBeenCalled();
  });

  it('should open info window when marker is clicked', () => {
    const mockOverlayClickEvent = {
      overlay: {
        getTitle(): string {
          return 'Title 1';
        }
      },
      originalEvent: {
        latLng: new google.maps.LatLng(40.0, 40.0)
      },
      map: {
      }
    };

    spyOn(component.mapInfoWindow, 'open');

    component.handleOverlayClick(mockOverlayClickEvent);

    expect(component.mapInfoWindow.getContent().toString().indexOf('Title 1') >= 0).toBeTrue();
    expect(component.mapInfoWindow.open).toHaveBeenCalled();
  });

  it('should not open info windo when not marker is clicked', () => {
    const mockOverlayClickEvent = {
      overlay: {}
    };

    spyOn(component.mapInfoWindow, 'open');

    component.handleOverlayClick(mockOverlayClickEvent);
    expect(component.mapInfoWindow.open).not.toHaveBeenCalled();

  });
});
