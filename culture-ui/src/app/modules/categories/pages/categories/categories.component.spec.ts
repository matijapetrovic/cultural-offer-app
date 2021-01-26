import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ConfirmationService } from 'primeng/api';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { mockCategoriesPage } from 'src/app/shared/testing/mock-data';
import { of } from 'rxjs';
import { MessageService } from 'primeng/api';

import { CategoriesComponent } from './categories.component';

describe('CategoriesComponent', () => {
  let component: CategoriesComponent;
  let fixture: ComponentFixture<CategoriesComponent>;

  beforeEach(async () => {
    const categoriesServiceMock = {
      getCategories: jasmine.createSpy('getCategories')
        .and.returnValue(of(mockCategoriesPage)),
      deleteCategory: jasmine.createSpy('deleteCategory')
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
        accept: jasmine.createSpy('accept').and.callFake(() => component.deleteCategory),
        reject: jasmine.createSpy('reject').and.callThrough(),
       },
    };

    await TestBed.configureTestingModule({
      declarations: [ CategoriesComponent ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule],
      providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock },
                  { provide: ConfirmationService, useValue: confirmationServiceMock},
                  { provide: CategoriesService, useValue: categoriesServiceMock },
                  { provide: MessageService, useValue: messageService }, ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('showAddForm() should add category when inputs are added', fakeAsync(() => {
    component.showAddForm();
    tick();

    spyOn(component, 'getCategories');
    spyOn(component.ref, 'close').and.returnValue(component.messageService.add({})).and.callFake(() => component.getCategories());
    component.ref.close();
    tick();

    expect(component.ref).toBeTruthy();
    expect(component.ref.close).toHaveBeenCalled();
    expect(component.getCategories).toHaveBeenCalled();
    expect(component.messageService.add).toHaveBeenCalled();
  }));

  it('showUpdateForm() should get categories when inputs are added', fakeAsync(() => {
    const category = {
      id: 1,
      name: 'Category'
    };
    spyOn(component, 'getCategories');
    component.showUpdateForm(category);
    tick();

    spyOn(component.ref, 'close').and.returnValue(component.messageService.add({})).and.callFake(() => component.getCategories());
    component.ref.close();
    tick();

    expect(component.ref).toBeTruthy();
    expect(component.ref.close).toHaveBeenCalled();
    expect(component.getCategories).toHaveBeenCalled();
    expect(component.messageService.add).toHaveBeenCalled();
  }));

  it('showDeleteForm() should delete category when accepted', fakeAsync(() => {
    const id = 1;
    spyOn(component, 'deleteCategory');
    spyOn<any>(component.confirmationService, 'confirm').and.callFake((params: any) => {
      component.messageService.add({});
      params.accept();
    });
    component.showDeleteForm(id);
    tick();

    expect(component.confirmationService.confirm).toBeTruthy();
    expect(component.deleteCategory).toHaveBeenCalled();
    expect(component.messageService.add).toHaveBeenCalled();
  }));

  it('showDeleteForm() reject should write rejecting a message', fakeAsync(() => {
    const id = 1;
    spyOn<any>(component.confirmationService, 'confirm').and.callFake((params: any) => {
      params.reject();
    });
    component.showDeleteForm(id);
    tick();

    expect(component.confirmationService.confirm).toBeTruthy();
  }));

  it('getCategories() should get categories', fakeAsync(() => {
    component.categoriesPage = null;
    component.getCategories();

    tick();

    expect(component.categoriesPage).toBeTruthy();
  }));

  it('deleteCategory(id) should delete category', fakeAsync(() => {
    const id = 1;

    spyOn(component, 'getCategories');
    component.deleteCategory(id);

    expect(component.getCategories).toHaveBeenCalled();
  }));

  it('getNextCategories() should get categories from next page', fakeAsync(() => {
    spyOn(component, 'getCategories');
    component.getNextCategories();

    expect(component.getCategories).toHaveBeenCalled();
  }));

  it('getPrevCategories() should get categories from previous page', fakeAsync(() => {
    spyOn(component, 'getCategories');
    component.getPrevCategories();

    expect(component.getCategories).toHaveBeenCalled();
  }));
});
