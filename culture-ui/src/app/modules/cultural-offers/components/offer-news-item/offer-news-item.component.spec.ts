import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LocalDatePipe } from 'src/app/shared/pipes/local-date.pipe';
import { SliceTextPipe } from 'src/app/shared/pipes/slice-text.pipe';
import { mockNews } from 'src/app/shared/testing/mock-data';

import { OfferNewsItemComponent } from './offer-news-item.component';

describe('OfferNewsItemComponent', () => {
  let component: OfferNewsItemComponent;
  let fixture: ComponentFixture<OfferNewsItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferNewsItemComponent, LocalDatePipe, SliceTextPipe ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferNewsItemComponent);
    component = fixture.componentInstance;
    component.newsItem = mockNews;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
