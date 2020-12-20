import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferNewsItemComponent } from './offer-news-item.component';

describe('OfferNewsItemComponent', () => {
  let component: OfferNewsItemComponent;
  let fixture: ComponentFixture<OfferNewsItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferNewsItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferNewsItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
