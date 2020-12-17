import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FlexLayoutModule } from '@angular/flex-layout';
import {MatButtonModule} from '@angular/material/button';
import { StarRatingModule } from 'angular-star-rating';


import { SharedModule } from './../../shared/shared.module';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';
import { CulturalOffersRoutingModule } from './cultural-offers-routing.module';
import { CulturalOfferMapComponent } from './pages/cultural-offer-map/cultural-offer-map.component';
import { OfferReviewsComponent } from './components/offer-reviews/offer-reviews.component';
import { OfferReviewItemComponent } from './components/offer-review-item/offer-review-item.component';


@NgModule({
  declarations: [CulturalOfferComponent, CulturalOfferMapComponent, OfferReviewsComponent, OfferReviewItemComponent],
  imports: [
    CommonModule,
    CulturalOffersRoutingModule,
    SharedModule,
    FlexLayoutModule,
    MatButtonModule,
    StarRatingModule.forRoot()
  ]
})
export class CulturalOffersModule { }
