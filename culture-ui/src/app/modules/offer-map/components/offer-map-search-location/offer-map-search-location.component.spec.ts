import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { OfferMapSearchLocationComponent } from './offer-map-search-location.component';

describe('OfferMapSearchLocationComponent', () => {
  let component: OfferMapSearchLocationComponent;
  let fixture: ComponentFixture<OfferMapSearchLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferMapSearchLocationComponent ],
      imports: [ FormsModule, ReactiveFormsModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferMapSearchLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.searchForm).toBeDefined();
  });

  it('raises the onSubmitEvent when form is submitted', () => {
    const location: string = 'Novi Sad Serbia';

    component.searchForm.controls['location'].setValue(location);

    let selectedLocation: string;
    component.onSubmit.subscribe((location: string) => selectedLocation = location);
    
    component.submit();
    expect(selectedLocation).toEqual(location);
    expect(component.searchForm.pristine).toBeTrue();
  });
});
