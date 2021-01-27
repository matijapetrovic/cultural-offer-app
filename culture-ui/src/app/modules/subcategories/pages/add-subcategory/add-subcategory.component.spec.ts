import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { DynamicDialogConfig, DynamicDialogRef } from "primeng/dynamicdialog";
import { SubcategoriesService } from "../../subcategories.service";
import { AddSubcategoryComponent } from "./add-subcategory.component";




describe('AddSubcategoryComponent', () => {

  let component: AddSubcategoryComponent;
  let fixture: ComponentFixture<AddSubcategoryComponent>;

  beforeEach(async () => {
    const subcategoriesServiceMock = {
        addSubcategory: jasmine.createSpy('addSubcategory')
        .and.returnValue({
          subscribe: () => {
            component.loading = false;
            component.removeFormInputs();
            component.ref.close(true);
          }})
    };

    const dialogRefMock = {
      close: () => { }
    };

    const dialogRefConfigMock = {
        data: {
          category: { id: 1, name: 'Category' }
        }
      };

    await TestBed.configureTestingModule({
      declarations: [AddSubcategoryComponent],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule],
      providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock},
                  { provide: DynamicDialogConfig, useValue: dialogRefConfigMock},
                  { provide: SubcategoriesService, useValue: subcategoriesServiceMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSubcategoryComponent);
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

  it('onSubmit() form with valid inputs should be submitted', () => {
    const name = 'Subategory';

    component.addForm.controls.name.setValue(name);
    component.onSubmit();

    expect(component.addForm.pristine).toBeTrue();
  });

  it('onSubmit() form with empty inputs should not be submitted', () => {
    const name = '';

    spyOn(component, 'invalidFormInputs');
    spyOn(component, 'removeFormInputs');
    component.addForm.controls.name.setValue(name);
    component.onSubmit();

    expect(component.invalidFormInputs).toHaveBeenCalled();
    expect(component.removeFormInputs).toHaveBeenCalled();
  });

  it('addSubcategory() form with valid inputs should be added', () => {
    const name = 'Subcategory';

    spyOn(component, 'removeFormInputs');
    component.addForm.controls.name.setValue(name);
    component.addSubcategory();

    expect(component.loading).toBeFalse();
    expect(component.removeFormInputs).toHaveBeenCalled();
  });

  it('removeFormInputs() should reset all form inputs', () => {
    spyOn(component.addForm, 'reset');

    component.removeFormInputs();

    expect(component.addForm.reset).toHaveBeenCalled();
  });

  it('invalidInputFarms() should return false if form inputs are valid', () => {
    const name = 'Category';

    component.addForm.controls.name.setValue(name);

    expect(component.invalidFormInputs()).toBeFalse();
  });

  it('invalidInputFarms() should return true if form inputs are invalid', () => {
    const name = '';

    component.addForm.controls.name.setValue(name);

    expect(component.invalidFormInputs()).toBeTrue();
  });

  it(`errorMessage() should return 'Name is required!'`, () => {
    const message = 'Name is required!';

    expect(component.errorMessage()).toEqual(message);
  });

});