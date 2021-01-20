import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferMapListComponent } from './offer-map-list.component';

describe('OfferMapListComponent', () => {
  let component: OfferMapListComponent;
  let fixture: ComponentFixture<OfferMapListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferMapListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferMapListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
