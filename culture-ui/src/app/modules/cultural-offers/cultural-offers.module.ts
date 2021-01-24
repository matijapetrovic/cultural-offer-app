import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from './../../shared/shared.module';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';
import { CulturalOffersRoutingModule } from './cultural-offers-routing.module';
import { OfferReviewItemComponent } from './components/offer-review-item/offer-review-item.component';
import { OfferNewsItemComponent } from './components/offer-news-item/offer-news-item.component';


@NgModule({
  declarations: [CulturalOfferComponent, OfferReviewItemComponent, OfferNewsItemComponent],
  imports: [
    CommonModule,
    CulturalOffersRoutingModule,
    SharedModule
  ]
})
export class CulturalOffersModule { }
