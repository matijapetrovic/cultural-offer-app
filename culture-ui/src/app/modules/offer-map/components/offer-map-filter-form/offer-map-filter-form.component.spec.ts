import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { Category } from 'src/app/modules/categories/category';
import { CulturalOfferLocationsFilter } from 'src/app/modules/cultural-offers/cultural-offer';
import { Subcategory } from 'src/app/modules/subcategories/subcategory';

import { OfferMapFilterFormComponent } from './offer-map-filter-form.component';

describe('OfferMapFilterFormComponent', () => {
  let component: OfferMapFilterFormComponent;
  let fixture: ComponentFixture<OfferMapFilterFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferMapFilterFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferMapFilterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.filterForm).toBeDefined();
  });

  it('raises the onSubmit when form is submitted', () => {
    const category: Category = {
      id: 1,
      name: 'Category 1'
    };

    const subcategory: Subcategory = {
      id: 1,
      categoryId: category.id,
      name: 'Subcategory 1'
    };
    component.filterForm.controls.category.setValue(category);
    component.selectCategory();
    component.filterForm.controls.subcategory.setValue(subcategory);

    let filter: CulturalOfferLocationsFilter;
    component.submit.subscribe((event: CulturalOfferLocationsFilter) => filter = event);

    component.submitForm();

    expect(filter.category).toBeDefined();
    expect(filter.category.id).toEqual(category.id);
    expect(filter.category.name).toEqual(category.name);

    expect(filter.subcategory).toBeDefined();
    expect(filter.subcategory.id).toEqual(subcategory.id);
    expect(filter.subcategory.name).toEqual(subcategory.name);
    expect(filter.subcategory.categoryId).toEqual(subcategory.categoryId);
  });

  it('raises onCategorySelected when category is selected', () => {
    const category: Category = {
      id: 1,
      name: 'Category 1'
    };

    component.filterForm.controls.category.setValue(category);

    let selectedCategory: Category;
    component.categorySelected.subscribe((event: Category) => selectedCategory = event);

    component.selectCategory();
    expect(selectedCategory).toBeDefined();
    expect(selectedCategory.id).toEqual(category.id);
    expect(selectedCategory.name).toEqual(category.name);
    expect(component.filterForm.controls.subcategory.pristine).toBeTrue();
    expect(component.filterForm.controls.subcategory.enabled).toBeTrue();
  });

  it('raises onReset when form is reset', () => {

    let reset = false;
    component.reset.subscribe(() => reset = true);

    component.resetForm();
    expect(reset).toBeTrue();
  });
});
