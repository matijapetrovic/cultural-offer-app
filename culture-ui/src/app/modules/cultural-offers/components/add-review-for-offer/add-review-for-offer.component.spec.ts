import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddReviewForOfferComponent } from './add-review-for-offer.component';

describe('AddReviewForOfferComponent', () => {
  let component: AddReviewForOfferComponent;
  let fixture: ComponentFixture<AddReviewForOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddReviewForOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddReviewForOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
