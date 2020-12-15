import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';

import { SharedModule } from './../../shared/shared.module';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';
import { CulturalOffersRoutingModule } from './cultural-offers-routing.module';
import { CulturalOfferMapComponent } from './pages/cultural-offer-map/cultural-offer-map.component';


@NgModule({
  declarations: [CulturalOfferComponent, CulturalOfferMapComponent],
  imports: [
    CommonModule,
    CulturalOffersRoutingModule,
    SharedModule,
    FlexLayoutModule
  ]
})
export class CulturalOffersModule { }
