import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferNewsComponent } from './offer-news.component';

describe('OfferNewsComponent', () => {
  let component: OfferNewsComponent;
  let fixture: ComponentFixture<OfferNewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferNewsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
