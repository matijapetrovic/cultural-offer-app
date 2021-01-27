import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewReplyItemComponent } from './review-reply-item.component';

describe('ReviewReplyItemComponent', () => {
  let component: ReviewReplyItemComponent;
  let fixture: ComponentFixture<ReviewReplyItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReviewReplyItemComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewReplyItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
