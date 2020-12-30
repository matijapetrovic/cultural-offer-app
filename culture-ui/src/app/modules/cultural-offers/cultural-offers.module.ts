import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from './../../shared/shared.module';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';
import { CulturalOffersRoutingModule } from './cultural-offers-routing.module';
import { OfferReviewsComponent } from './components/offer-reviews/offer-reviews.component';
import { OfferReviewItemComponent } from './components/offer-review-item/offer-review-item.component';
import { OfferNewsComponent } from './components/offer-news/offer-news.component';
import { OfferNewsItemComponent } from './components/offer-news-item/offer-news-item.component';


@NgModule({
  declarations: [CulturalOfferComponent, OfferReviewsComponent, OfferReviewItemComponent, OfferNewsComponent, OfferNewsItemComponent],
  imports: [
    CommonModule,
    CulturalOffersRoutingModule,
    SharedModule
  ]
})
export class CulturalOffersModule { }
