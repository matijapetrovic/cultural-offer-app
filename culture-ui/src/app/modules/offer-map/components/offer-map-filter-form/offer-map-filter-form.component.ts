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
  onSubcategorySelected = new EventEmitter<Category>();

  @Output()
  onSubmit = new EventEmitter<CulturalOfferLocationsFilter>();

  @Output()
  onReset = new EventEmitter<void>();

  filterForm: FormGroup;

  constructor() {
    this.filterForm = this.createFormGroup();
  }

  createFormGroup(): FormGroup {
    return new FormGroup({
      category: new FormControl(),
      subcategory: new FormControl({value: null, disabled:true})
    });
  }

  ngOnInit(): void {
  }

  // resetuj van komponente 
  revert(): void {
    this.filterForm.reset();
    this.filterForm.controls['subcategory'].disable();
    this.onReset.emit();
  }

  emitSubcategorySelected(event: any): void {
    this.filterForm.controls['subcategory'].reset();
    this.filterForm.controls['subcategory'].enable();
    this.onSubcategorySelected.emit(event.value);
  }

  emitOnSubmit(): void {
    //deep copy
    console.log("uso");
    const result: CulturalOfferLocationsFilter = Object.assign({}, this.filterForm.value);
    result.category = Object.assign({}, result.category);
    result.subcategory = Object.assign({}, result.subcategory);

    this.onSubmit.emit(result);
  }

}
