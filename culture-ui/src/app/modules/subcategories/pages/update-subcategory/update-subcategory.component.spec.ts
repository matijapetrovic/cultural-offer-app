import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';
import { UpdateSubcategoryComponent } from './update-subcategory.component';
import { SubcategoriesService } from '../../subcategories.service';

describe('UpdateSubcategoryComponent', () => {
  let component: UpdateSubcategoryComponent;
  let config: DynamicDialogConfig;
  let fixture: ComponentFixture<UpdateSubcategoryComponent>;

  beforeEach(async () => {
    const subcategoriesServiceMock = {
      updateSubcategory: jasmine.createSpy('updateSubcategory')
        .and.returnValue({subscribe: () => { component.ref.close(true)}})
      };

    const dialogRefMock = {
      close: () => { }
    };

    const dialogRefConfigMock = {
      data: {
        subcategory: { 
            id: 1,
            name: 'Subcategory1',
            categoryId: 1
        }
      }
    };

    await TestBed.configureTestingModule({
      declarations: [ UpdateSubcategoryComponent ],
       imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule],
      providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock },
                  { provide: DynamicDialogConfig, useValue: dialogRefConfigMock},
                  { provide: SubcategoriesService, useValue: subcategoriesServiceMock }]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateSubcategoryComponent);
    component = fixture.componentInstance;
    config = TestBed.inject(DynamicDialogConfig);
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
    const name = 'Subcategory';

    spyOn(component, 'updateSubcategory');
    component.updateForm.controls.name.setValue(name);
    component.onSubmit();

    expect(component.updateForm.pristine).toBeTrue();
    expect(component.updateSubcategory).toHaveBeenCalled();
  });

  it('onSubmit() form with empty inputs should not be submitted', () => {
    const name = '';

    spyOn(component, 'invalidFormInputs');
    component.updateForm.controls.name.setValue(name);
    component.subcategory.name = name;
    component.onSubmit();

    expect(component.invalidFormInputs).toHaveBeenCalled();
  });

  it('updateSubcategory() form with valid inputs should be added', () => {

    spyOn(component, 'invalidFormInputs');
    component.updateForm.controls.name.setValue(name);
    component.updateSubcategory();

    expect(component.loading).toBeFalse();
  });

  it('invalidInputForms() should return false if form inputs are valid', () => {
    
    component.subcategory.name = '?';
    fixture.detectChanges();

    expect(component.invalidFormInputs()).toBeFalse();
  });

  it('invalidInputForms() should return true if form inputs are invalid', () => {
    component.subcategory.name = "";
    fixture.detectChanges();

    expect(component.invalidFormInputs()).toBeTrue();
  });

  it('nameNotChanged() should return true when names are equal', () => {
    
    component.subcategory.name = config.data.subcategory.name;

    expect(component.nameNotChanged()).toBeTrue();
  });

  it(`errorMessage() should return 'Name is required!'`, () => {
    const message = 'Name is required!';
    
    expect(component.errorMessage()).toEqual(message);
  });
});
