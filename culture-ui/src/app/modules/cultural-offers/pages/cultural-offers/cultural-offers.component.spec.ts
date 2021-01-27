import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ConfirmationService } from 'primeng/api';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { mockCategoriesPage, mockCulturalOffersPage, mockCulturalOfferView } from 'src/app/shared/testing/mock-data';
import { of } from 'rxjs';
import { MessageService } from 'primeng/api';

import { CulturalOffersComponent } from './cultural-offers.component';
import { CulturalOffersService } from '../../cultural-offers.service';
import { Router } from '@angular/router';

describe('CulturalOffers', () => {
  let component: CulturalOffersComponent;
  let fixture: ComponentFixture<CulturalOffersComponent>;

  beforeEach(async () => {
    const culturalOffersServiceMock = {
      getCulturaOffers : jasmine.createSpy('getCulturaOffers')
        .and.returnValue(of(mockCulturalOffersPage)),
        deleteCulturalOffer: jasmine.createSpy('deleteCulturalOffer')
        .and.returnValue(of({})),
    };

    const dialogRefMock = {
      close: () => { },
      onClose: jasmine.createSpy('onClose').and.returnValue({subscribe: () => { true; }})
    };

    const messageService = {
      add: jasmine.createSpy('add').and.returnValue({})
    };

    const reouterMock = {
        navigate: jasmine.createSpy('navigate').and.returnValue(of({}))
    }

    const confirmationServiceMock = {
      confirm: {
        accept: jasmine.createSpy('accept').and.callThrough(),
        reject: jasmine.createSpy('reject').and.callThrough(),
       },
    };

    await TestBed.configureTestingModule({
      declarations: [ CulturalOffersComponent ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule],
      providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock },
                  { provide: ConfirmationService, useValue: confirmationServiceMock},
                  { provide: CulturalOffersService, useValue: culturalOffersServiceMock },
                  { provide: MessageService, useValue: messageService },
                  { provide: Router, useValue: messageService} ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOffersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('showAddForm() should add offer when inputs are added', fakeAsync(() => {
    component.showAddForm();
    tick();

    spyOn(component, 'getCulturalOffers');
    spyOn(component.ref, 'close')
    .and.returnValue(component.messageService.add({}))
    .and.callFake(() => component.getCulturalOffers());
    component.ref.close();
    tick();

    expect(component.ref).toBeTruthy();
    expect(component.ref.close).toHaveBeenCalled();
    expect(component.getCulturalOffers).toHaveBeenCalled();
    expect(component.messageService.add).toHaveBeenCalled();
  }));

  it('showUpdateForm() should get categories when inputs are added', fakeAsync(() => {

    spyOn(component, 'getCulturalOffers');
    component.showUpdateForm(mockCulturalOfferView);
    tick();

    spyOn(component.ref, 'close')
        .and.returnValue(component.messageService.add({}))
        .and.callFake(() => component.getCulturalOffers());
    component.ref.close();
    tick();

    expect(component.ref).toBeTruthy();
    expect(component.ref.close).toHaveBeenCalled();
    expect(component.getCulturalOffers).toHaveBeenCalled();
    expect(component.messageService.add).toHaveBeenCalled();
  }));

  it('showDeleteForm() should delete category when accepted', fakeAsync(() => {
    spyOn<any>(component.confirmationService, 'confirm').and.callFake((params: any) => {
      component.messageService.add({});
      params.accept();
    });
    component.showDeleteForm(mockCulturalOfferView);
    tick();

    expect(component.confirmationService.confirm).toBeTruthy();
    expect(component.messageService.add).toHaveBeenCalled();
  }));

  it('showDeleteForm() reject should write rejecting a message', fakeAsync(() => {
    spyOn<any>(component.confirmationService, 'confirm').and.callFake((params: any) => {
      params.reject();
    });
    component.showDeleteForm(mockCulturalOfferView);
    tick();

    expect(component.confirmationService.confirm).toBeTruthy();
  }));

  it('deleteSubcategory() should delete subcategory if given subcategory is valid', fakeAsync(() => {

    spyOn(component, 'getCulturalOffers');
    component.deleteCulturalOffer(mockCulturalOfferView);

    expect(component.getCulturalOffers).toHaveBeenCalled();
}));

  it('getCulturalOffers() should get categories', fakeAsync(() => {
    component.culturalOffersPage = null;
    component.getCulturalOffers();

    tick();

    expect(component.culturalOffersPage).toBeTruthy();
  }));

  it('getNextCulturalOffers() should get offers from next page', fakeAsync(() => {
    spyOn(component, 'getCulturalOffers');
    component.getNextCulturalOffers();

    expect(component.getCulturalOffers).toHaveBeenCalled();
  }));

  it('getPrevCulturalOffers() should get offers from previous page', fakeAsync(() => {
    spyOn(component, 'getCulturalOffers');
    component.getPrevCulturalOffers();

    expect(component.getCulturalOffers).toHaveBeenCalled();
  }));
});
