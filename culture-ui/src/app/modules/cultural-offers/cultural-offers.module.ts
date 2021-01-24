import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FileUploadModule } from 'primeng/fileupload';
import { SharedModule } from './../../shared/shared.module';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';
import { CulturalOffersRoutingModule } from './cultural-offers-routing.module';
import { OfferReviewItemComponent } from './components/offer-review-item/offer-review-item.component';
import { OfferNewsItemComponent } from './components/offer-news-item/offer-news-item.component';
import { ReviewReplyItemComponent } from './components/review-reply-item/review-reply-item.component';
import { AddReplyForOfferComponent } from './components/add-reply-for-offer/add-reply-for-offer.component';
import { ReviewsModule } from '../reviews/reviews.module';
import { RepliesModule } from '../replies/replies.module';


@NgModule({
  declarations: [
    CulturalOfferComponent,
    OfferReviewItemComponent,
    OfferNewsItemComponent,
    ReviewReplyItemComponent,
    AddReplyForOfferComponent
  ],
  imports: [
    CommonModule,
    CulturalOffersRoutingModule,
    SharedModule,
    FileUploadModule,
    ReviewsModule,
    RepliesModule
  ]
})
export class CulturalOffersModule { }
