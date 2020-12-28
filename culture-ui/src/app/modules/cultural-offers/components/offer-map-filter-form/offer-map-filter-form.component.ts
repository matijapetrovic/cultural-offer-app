import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Category } from 'src/app/modules/categories/category';
import { Subcategory } from 'src/app/modules/subcategories/subcategory';
import { CulturalOfferLocationsFilter } from '../../cultural-offer';
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
  onSubcategorySelected = new EventEmitter<Category>();

  @Output()
  onSubmit = new EventEmitter<CulturalOfferLocationsFilter>();

  filterForm: FormGroup;

  constructor() {
    this.filterForm = this.createFormGroup();
  }

  createFormGroup(): FormGroup {
    return new FormGroup({
      category: new FormControl(),
      subcategory: new FormControl()
    });
  }

  ngOnInit(): void {
  }

  revert(): void {
    this.filterForm.reset();
  }

  emitSubcategorySelected(event: any): void {
    this.onSubcategorySelected.emit(event.value);
  }

  emitOnSubmit(): void {
    //deep copy
    const result: CulturalOfferLocationsFilter = Object.assign({}, this.filterForm.value);
    result.category = Object.assign({}, result.category);
    result.subcategory = Object.assign({}, result.subcategory);

    this.onSubmit.emit(result);
  }

}
