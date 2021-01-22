import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { UpdateCategoryComponent } from './update-category.component';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';

describe('UpdateCategoryComponent', () => {
  let component: UpdateCategoryComponent;
  let fixture: ComponentFixture<UpdateCategoryComponent>;
  const dialogRefMock = {
    close: () => { }
  };
  const dialogRefConfigMock = {
    data: {
      category: {id: 1, name: 'Category'}
    }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateCategoryComponent ],
       imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule],
      providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock },
        { provide: DynamicDialogConfig, useValue: dialogRefConfigMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.updateForm).toBeDefined();
  });

  it('form valid when empty', () => {
    expect(component.updateForm.valid).toBeTruthy();

  });

  it('onSubmit() form with vaid inputs should be submitted', () => {
    const name = 'Category';

    spyOn(component, 'updateCategory');
    component.updateForm.controls.name.setValue(name);
    component.name = name;
    component.onSubmit();

    expect(component.updateForm.pristine).toBeTrue();
  });

  it('onSubmit() form with empty inputs should not be submitted', () => {
    const name = '';

    spyOn(component, 'invalidFormInputs');
    component.updateForm.controls.name.setValue(name);
    component.onSubmit();

    expect(component.invalidFormInputs).toHaveBeenCalled();
  });

  it('updateCategory() form with valid inputs should be added', () => {
    const name = '';

    spyOn(component, 'invalidFormInputs');
    component.updateForm.controls.name.setValue(name);
    component.updateCategory();

    expect(component.loading).toBeFalse();
  });

  it('invalidInputForms() should return false if form inputs are valid', () => {
    const name = 'Category';
    const name2 = 'Category2';

    component.name = name;
    component.category.name = name2;
    component.updateForm.controls.name.setValue(name);

    expect(component.invalidFormInputs()).toBeFalse();
  });

  it('invalidInputForms() should return true if form inputs are invalid', () => {
    const name = '';

    component.updateForm.controls.name.setValue(name);

    expect(component.invalidFormInputs()).toBeTrue();
  });

  it('areNamesSame() should return true when names are equal', () => {
    const name = 'Category';

    component.name = name;
    component.category.name = name;

    expect(component.areNamesSame()).toBeTrue();
  });

  it('areNamesSame() should return false when names are not equal', () => {
    const name = 'Category';
    const name2 = 'Category2';

    component.name = name;
    component.category.name = name2;

    expect(component.areNamesSame()).toBeFalse();
  });
});
