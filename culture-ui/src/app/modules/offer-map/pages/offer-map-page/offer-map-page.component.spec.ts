import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferMapPageComponent } from './offer-map-page.component';

describe('OfferMapPageComponent', () => {
  let component: OfferMapPageComponent;
  let fixture: ComponentFixture<OfferMapPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferMapPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferMapPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
