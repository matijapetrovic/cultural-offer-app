import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from './../../shared/shared.module';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';
import { CulturalOffersRoutingModule } from './cultural-offers-routing.module';
import { CulturalOfferMapComponent } from './pages/cultural-offer-map/cultural-offer-map.component';
import { OfferReviewsComponent } from './components/offer-reviews/offer-reviews.component';
import { OfferReviewItemComponent } from './components/offer-review-item/offer-review-item.component';
import { OfferNewsComponent } from './components/offer-news/offer-news.component';
import { OfferNewsItemComponent } from './components/offer-news-item/offer-news-item.component';
import { OfferMapComponent } from './components/offer-map/offer-map.component';
import { OfferMapFilterFormComponent } from './components/offer-map-filter-form/offer-map-filter-form.component';


@NgModule({
  declarations: [CulturalOfferComponent, CulturalOfferMapComponent, OfferReviewsComponent, OfferReviewItemComponent, OfferNewsComponent, OfferNewsItemComponent, OfferMapComponent, OfferMapFilterFormComponent],
  imports: [
    CommonModule,
    CulturalOffersRoutingModule,
    SharedModule
  ]
})
export class CulturalOffersModule { }
