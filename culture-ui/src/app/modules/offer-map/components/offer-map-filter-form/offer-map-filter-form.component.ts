import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Category } from 'src/app/modules/categories/category';
import { Subcategory } from 'src/app/modules/subcategories/subcategory';
import { CulturalOfferLocationsFilter } from '../../../cultural-offers/cultural-offer';
@Component({
  selector: 'app-offer-map-filter-form',
  templateUrl: './offer-map-filter-form.component.html',
  styleUrls: ['./offer-map-filter-form.component.scss']
})
export class OfferMapFilterFormComponent implements OnInit {
  @Input()
  categories: Category[];
  @Input()
  subcategories: Subcategory[];

  @Output()
  onCategorySelected = new EventEmitter<Category>();

  @Output()
  onSubmit = new EventEmitter<CulturalOfferLocationsFilter>();

  @Output()
  onReset = new EventEmitter<void>();

  filterForm: FormGroup;

  constructor() {
    this.filterForm = new FormGroup({
      category: new FormControl(),
      subcategory: new FormControl({value: null, disabled: true})
    });
  }

  ngOnInit(): void {
  }

  reset(): void {
    this.filterForm.reset();
    this.filterForm.controls.subcategory.disable();
    this.onReset.emit();
  }

  selectCategory(): void {
    this.filterForm.controls.subcategory.reset();
    this.filterForm.controls.subcategory.enable();
    this.onCategorySelected.emit(Object.assign({}, this.filterForm.value.category));
  }

  submit(): void {
    // deep copy
    const result: CulturalOfferLocationsFilter = Object.assign({}, this.filterForm.value);
    result.category = Object.assign({}, result.category);
    result.subcategory = Object.assign({}, result.subcategory);
    this.onSubmit.emit(result);
  }

}
