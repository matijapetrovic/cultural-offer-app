import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { mockCulturalOfferToAdd } from 'src/app/shared/testing/mock-data';
import { CulturalOffersService } from '../../cultural-offers.service';
import { AddOfferComponent } from './add-offer.component';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';


describe('AddOfferComponent', () => {

    let component: AddOfferComponent;
    let fixture: ComponentFixture<AddOfferComponent>;

    beforeEach(async () => {

        const culturalOffersServiceMock = {
            addCulturalOffer : jasmine.createSpy('addCulturalOffer')
              .and.returnValue({
                subscribe: () => {
                    component.loading = false;
                    component.ref.close(true);
                }
              })
          };

        const dialogRefMock = {
            close: () => { },
            onClose: jasmine.createSpy('onClose').and.returnValue({subscribe: () => { true; }})
          };

        await TestBed.configureTestingModule({
            declarations: [AddOfferComponent],
            imports: [HttpClientTestingModule],
            providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock},
                        { provide: CulturalOffersService, useValue: culturalOffersServiceMock,
                          DynamicDialogConfig }]
          })
          .compileComponents();

    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AddOfferComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

    it('should create', () => {
        expect(component).toBeTruthy();
      });

    it('postOffer() should post offer when form is submited', fakeAsync(() => {

        spyOn(component.ref, 'close');

        component.postOffer(mockCulturalOfferToAdd);

        expect(component.loading).toBeFalsy();
        expect(component.ref.close).toHaveBeenCalled();
      }));

});
