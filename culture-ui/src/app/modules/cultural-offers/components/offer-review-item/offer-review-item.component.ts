import { Component, Input, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { Role } from 'src/app/modules/authentication/role';
import { Review } from 'src/app/modules/reviews/review';

@Component({
  selector: 'app-offer-review-item',
  templateUrl: './offer-review-item.component.html',
  styleUrls: ['./offer-review-item.component.scss']
})
export class OfferReviewItemComponent implements OnInit {
  @Input()
  review: Review;

  userIsAdmin: boolean;

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.authenticationService.currentUser.subscribe((user) => this.userIsAdmin = user && user.role === Role.Admin);
  }

}
