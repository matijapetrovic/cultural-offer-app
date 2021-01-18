import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http'; 
import { DynamicDialogRef } from 'primeng/dynamicdialog';

import { AddCategoryComponent } from './add-category.component';

describe('AddCategoryComponent', () => {
  let component: AddCategoryComponent;
  let fixture: ComponentFixture<AddCategoryComponent>;

  beforeEach(async () => {
    const dialogRefMock = {
      close: () => { }
    };

    await TestBed.configureTestingModule({
      declarations: [AddCategoryComponent],
      imports: [
        FormsModule, 
        ReactiveFormsModule, 
        HttpClientTestingModule],
      providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.addForm).toBeDefined();
  });

  it('form invalid when empty', () => {
    expect(component.addForm.valid).toBeFalsy();
  });

  it('onSubmit() form with vaid inputs should be submitted', () => {
    let name = 'Category';

    component.addForm.controls['name'].setValue(name);
    component.onSubmit();

    expect(component.addForm.pristine).toBeTrue();
  });

  it('onSubmit() form with empty inputs should not be submitted', () => {
    const name: string = '';

    spyOn(component, 'invalidFormInputs');
    component.addForm.controls['name'].setValue(name);
    component.onSubmit();

    expect(component.invalidFormInputs).toHaveBeenCalled();
  });

  it('addCategory() form with valid inputs should be added', () => {
    const name: string = '';

    spyOn(component, 'invalidFormInputs');
    component.addForm.controls['name'].setValue(name);
    component.addCategory();

    expect(component.loading).toBeFalse();
  });

  it('removeFormInputs() should reset all form inputs', () => {
    spyOn(component.addForm, 'reset');

    component.removeFormInputs();

    expect(component.addForm.reset).toHaveBeenCalled();
  });

  it('invalidInputFarms() should return false if form inputs are valid', () => {
    const name: string = 'Category';

    component.addForm.controls['name'].setValue(name);

    expect(component.invalidFormInputs()).toBeFalse();
  });

  it('invalidInputFarms() should return true if form inputs are invalid', () => {
    const name: string = '';

    component.addForm.controls['name'].setValue(name);

    expect(component.invalidFormInputs()).toBeTrue();
  });

});
