import { Component, Input, OnInit } from '@angular/core';
import { Review } from 'src/app/modules/reviews/review';

@Component({
  selector: 'app-offer-reviews',
  templateUrl: './offer-reviews.component.html',
  styleUrls: ['./offer-reviews.component.scss']
})
export class OfferReviewsComponent implements OnInit {
  @Input()
  reviews: Review[];
  
  constructor() { }

  ngOnInit(): void {
  }

}
