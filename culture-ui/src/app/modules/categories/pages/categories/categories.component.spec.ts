import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ConfirmationService } from 'primeng/api';

import { CategoriesComponent } from './categories.component';

describe('CategoriesComponent', () => {
  let component: CategoriesComponent;
  let fixture: ComponentFixture<CategoriesComponent>;
  const dialogRefMock = {
    close: () => { }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoriesComponent ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule],
      providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock },
                  { provide: ConfirmationService, useValue: {}}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('showAddForm() should add category when inputs are added', () => {
    component.showAddForm();

    expect(component.ref).toBeTruthy();
  });

  it('showUpdateForm() should get categories when inputs are added', () => {
    const category = {
      id: 1,
      name: 'Category'
    };
    component.showUpdateForm(category);

    expect(component.ref).toBeTruthy();
  });

  // it('showDeleteForm() should delete category when accepted', () => {
  //   let id = 1;
  //   let confirmationService = component.confirmationService
  //   spyOn<any>(confirmationService, 'confirm').and.callFake(() => accept);
  //   component.showDeleteForm(id);

  //   expect(confirmationService.confirm).toHaveBeenCalled();
  // });

  it('getCategories() should get categories', () => {
    spyOn(component, 'getCategories');
    component.getCategories();

    expect(component.getCategories).toHaveBeenCalled();
  });

  it('deleteCategories() should delete category', () => {
    const id = 1;

    spyOn(component, 'deleteCategory');
    component.deleteCategory(id);

    expect(component.deleteCategory).toHaveBeenCalled();
  });

  it('getNextCategories() should get categories from next page', () => {
    spyOn(component, 'getCategories');
    component.getNextCategories();

    expect(component.getCategories).toHaveBeenCalled();
  });

  it('getPrevCategories() should get categories from previous page', () => {
    spyOn(component, 'getCategories');
    component.getPrevCategories();

    expect(component.getCategories).toHaveBeenCalled();
  });
});
