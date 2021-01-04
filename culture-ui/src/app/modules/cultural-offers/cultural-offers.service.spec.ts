import { TestBed } from '@angular/core/testing';

import { CulturalOffersService } from './cultural-offers.service';

describe('CulturalOffersService', () => {
  let service: CulturalOffersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CulturalOffersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
