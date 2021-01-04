import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferReviewsComponent } from './offer-reviews.component';

describe('OfferReviewsComponent', () => {
  let component: OfferReviewsComponent;
  let fixture: ComponentFixture<OfferReviewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferReviewsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
