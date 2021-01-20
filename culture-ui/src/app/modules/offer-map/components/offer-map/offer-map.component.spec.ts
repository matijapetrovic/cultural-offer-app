import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LocationRange } from 'src/app/modules/cultural-offers/cultural-offer';
import { mockCulturalOffer, mockOfferLocations } from 'src/app/shared/testing/mock-data';

import { OfferMapComponent } from './offer-map.component';

describe('OfferMapComponent', () => {
  let component: OfferMapComponent;
  let fixture: ComponentFixture<OfferMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferMapComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferMapComponent);
    component = fixture.componentInstance;
    component.culturalOffers = mockOfferLocations;
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
