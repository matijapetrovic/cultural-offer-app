import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddReplyForOfferComponent } from './add-reply-for-offer.component';

describe('AddReplyForOfferComponent', () => {
  let component: AddReplyForOfferComponent;
  let fixture: ComponentFixture<AddReplyForOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddReplyForOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddReplyForOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
