import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferMapComponent } from './cultural-offer-map.component';

describe('CulturalOfferMapComponent', () => {
  let component: CulturalOfferMapComponent;
  let fixture: ComponentFixture<CulturalOfferMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferMapComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
