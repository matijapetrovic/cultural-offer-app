import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-offer-map-search-location',
  templateUrl: './offer-map-search-location.component.html',
  styleUrls: ['./offer-map-search-location.component.scss']
})
export class OfferMapSearchLocationComponent implements OnInit {
  searchForm: FormGroup;

  @Output()
  submitted = new EventEmitter<string>();

  constructor() {
    this.searchForm = new FormGroup({
      location: new FormControl()
    });
  }

  ngOnInit(): void {
  }

  submitForm(): void {
    this.submitted.emit(this.searchForm.value.location);
    this.searchForm.reset();
  }
}
