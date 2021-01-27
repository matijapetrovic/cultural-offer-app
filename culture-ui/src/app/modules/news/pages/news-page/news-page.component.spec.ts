import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ConfirmationService } from 'primeng/api';
import { mockCulturalOffersPage, mockCulturalOfferView, mockNewsPage, mockNewsView } from 'src/app/shared/testing/mock-data';
import { of } from 'rxjs';
import { MessageService } from 'primeng/api';

import { ActivatedRoute, Data, Params, Router } from '@angular/router';
import { NewsPageComponent } from './news-page.component';
import { NewsService } from '../../news.service';
import { ActivatedRouteStub } from 'src/app/shared/testing/router-stubs';

describe('NewsPageComponent', () => {
  let component: NewsPageComponent;
  let fixture: ComponentFixture<NewsPageComponent>;

  beforeEach(async () => {
    const newsServiceMock = {
      getNews : jasmine.createSpy('getNews')
        .and.returnValue(of(mockNewsPage)),
        deleteNews: jasmine.createSpy('deleteNews')
        .and.returnValue(of({})),
    };

    const dialogRefMock = {
      close: () => { },
      onClose: jasmine.createSpy('onClose').and.returnValue({subscribe: () => { true; }})
    };

    const messageService = {
      add: jasmine.createSpy('add').and.returnValue({})
    };

    const confirmationServiceMock = {
      confirm: {
        accept: jasmine.createSpy('accept').and.callThrough(),
        reject: jasmine.createSpy('reject').and.callThrough(),
       },
    };

    const activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = {offerId: 1};
  

    await TestBed.configureTestingModule({
      declarations: [ NewsPageComponent ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule],
      providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock },
                  { provide: ConfirmationService, useValue: confirmationServiceMock},
                  { provide: NewsService, useValue: newsServiceMock },
                  { provide: MessageService, useValue: messageService },
                  { provide: ActivatedRoute, useValue: activatedRouteStub }]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('showAddForm() should add news when inputs are added', fakeAsync(() => {
    component.showAddForm();
    tick();

    spyOn(component, 'getNews');
    spyOn(component.ref, 'close')
    .and.returnValue(component.messageService.add({}))
    .and.callFake(() => component.getNews());
    component.ref.close();
    tick();

    expect(component.ref).toBeTruthy();
    expect(component.ref.close).toHaveBeenCalled();
    expect(component.getNews).toHaveBeenCalled();
    expect(component.messageService.add).toHaveBeenCalled();
  }));

  it('showUpdateForm() should get news when inputs are added', fakeAsync(() => {

    spyOn(component, 'getNews');
    component.showUpdateForm(mockNewsView);
    tick();

    spyOn(component.ref, 'close')
        .and.returnValue(component.messageService.add({}))
        .and.callFake(() => component.getNews());
    component.ref.close();
    tick();

    expect(component.ref).toBeTruthy();
    expect(component.ref.close).toHaveBeenCalled();
    expect(component.getNews).toHaveBeenCalled();
    expect(component.messageService.add).toHaveBeenCalled();
  }));

  it('showDeleteForm() should delete news when accepted', fakeAsync(() => {
    spyOn<any>(component.confirmationService, 'confirm').and.callFake((params: any) => {
      component.messageService.add({});
      params.accept();
    });
    component.showDeleteForm(mockNewsView);
    tick();

    expect(component.confirmationService.confirm).toBeTruthy();
    expect(component.messageService.add).toHaveBeenCalled();
  }));

  it('showDeleteForm() reject should write rejecting a message', fakeAsync(() => {
    spyOn<any>(component.confirmationService, 'confirm').and.callFake((params: any) => {
      params.reject();
    });
    component.showDeleteForm(mockNewsView);
    tick();

    expect(component.confirmationService.confirm).toBeTruthy();
  }));

  it('getNews() should get news', fakeAsync(() => {
    component.newsPage = null;
    component.getNews();

    tick();

    expect(component.newsPage).toBeTruthy();
  }));

  it('getNextNews() should get offers from next page', fakeAsync(() => {
    spyOn(component, 'getNews');
    component.getNextNews();

    expect(component.getNews).toHaveBeenCalled();
  }));

  it('getPrevNews() should get offers from previous page', fakeAsync(() => {
    spyOn(component, 'getNews');
    component.getPrevNews();

    expect(component.getNews).toHaveBeenCalled();
  }));
});
