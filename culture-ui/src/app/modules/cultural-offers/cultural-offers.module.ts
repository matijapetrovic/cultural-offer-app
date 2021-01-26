import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FileUploadModule } from 'primeng/fileupload';
import { SharedModule } from './../../shared/shared.module';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';
import { CulturalOffersRoutingModule } from './cultural-offers-routing.module';
import { OfferReviewItemComponent } from './components/offer-review-item/offer-review-item.component';
import { ReviewReplyItemComponent } from './components/review-reply-item/review-reply-item.component';
import { ReviewsModule } from '../reviews/reviews.module';


@NgModule({
  declarations: [
    CulturalOfferComponent,
    OfferReviewItemComponent,
    ReviewReplyItemComponent,
  ],
  imports: [
    CommonModule,
    CulturalOffersRoutingModule,
    SharedModule,
    FileUploadModule,
    ReviewsModule
  ]
})
export class CulturalOffersModule { }
