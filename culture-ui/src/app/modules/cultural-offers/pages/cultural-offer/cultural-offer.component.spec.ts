import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferComponent } from './cultural-offer.component';

describe('CulturalOfferComponent', () => {
  let component: CulturalOfferComponent;
  let fixture: ComponentFixture<CulturalOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
