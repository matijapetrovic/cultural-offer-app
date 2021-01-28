import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { mockCulturalOfferToAdd, mockCulturalOfferView } from 'src/app/shared/testing/mock-data';
import { CulturalOffersService } from '../../cultural-offers.service';
import { UpdateOfferComponent } from './update-offer.component';


describe('UpdateOfferComponent', () => {

    let component: UpdateOfferComponent;
    let fixture: ComponentFixture<UpdateOfferComponent>;

    beforeEach(async () => {

        const culturalOffersServiceMock = {
            updateCulturalOffer : jasmine.createSpy('updateCulturalOffer')
              .and.returnValue({
                subscribe: () => {
                    component.loading = false;
                    component.ref.close(true);
                }
              })
          };

        const dialogRefMock = {
            close: () => { },
            onClose: jasmine.createSpy('onClose').and.returnValue({subscribe: () => { }})
          };

        const dialogRefConfigMock = {
            data: {
              offer: mockCulturalOfferView
            }
          };

        await TestBed.configureTestingModule({
            declarations: [UpdateOfferComponent],
            imports: [HttpClientTestingModule],
            providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock},
                        { provide: CulturalOffersService, useValue: culturalOffersServiceMock },
                        { provide: DynamicDialogConfig, useValue: dialogRefConfigMock }]
          })
          .compileComponents();

    });

    beforeEach(() => {
        fixture = TestBed.createComponent(UpdateOfferComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

    it('should create', () => {
        expect(component).toBeTruthy();
      });

    it('updateOffer() should put offer when form is submited', fakeAsync(() => {

        spyOn(component.ref, 'close');

        component.updateOffer(mockCulturalOfferToAdd);

        expect(component.loading).toBeFalsy();
        expect(component.ref.close).toHaveBeenCalled();
      }));

});
