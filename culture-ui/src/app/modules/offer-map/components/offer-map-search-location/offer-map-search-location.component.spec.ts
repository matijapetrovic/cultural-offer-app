import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferMapSearchLocationComponent } from './offer-map-search-location.component';

describe('OfferMapSearchLocationComponent', () => {
  let component: OfferMapSearchLocationComponent;
  let fixture: ComponentFixture<OfferMapSearchLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferMapSearchLocationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferMapSearchLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
