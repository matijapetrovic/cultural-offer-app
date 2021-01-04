import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferMapFilterFormComponent } from './offer-map-filter-form.component';

describe('OfferMapFilterFormComponent', () => {
  let component: OfferMapFilterFormComponent;
  let fixture: ComponentFixture<OfferMapFilterFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferMapFilterFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferMapFilterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
