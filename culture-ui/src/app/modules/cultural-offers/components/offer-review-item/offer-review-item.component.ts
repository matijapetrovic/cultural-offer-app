import { Component, Input, OnInit } from '@angular/core';
import { Review } from 'src/app/modules/reviews/review';

@Component({
  selector: 'app-offer-review-item',
  templateUrl: './offer-review-item.component.html',
  styleUrls: ['./offer-review-item.component.scss']
})
export class OfferReviewItemComponent implements OnInit {
  @Input()
  review: Review;
  constructor() { }

  ngOnInit(): void {
  }

}
