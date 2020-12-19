import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferReviewItemComponent } from './offer-review-item.component';

describe('OfferReviewItemComponent', () => {
  let component: OfferReviewItemComponent;
  let fixture: ComponentFixture<OfferReviewItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferReviewItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferReviewItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
