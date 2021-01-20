import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OfferMapPageComponent } from './pages/offer-map-page/offer-map-page.component';

import {OfferMapComponent} from './components/offer-map/offer-map.component';
import {OfferMapFilterFormComponent} from './components/offer-map-filter-form/offer-map-filter-form.component';

import {OfferMapRoutingModule} from './offer-map-routing.module';

import { SharedModule } from './../../shared/shared.module';
import { OfferMapListComponent } from './components/offer-map-list/offer-map-list.component';
import { OfferMapSearchLocationComponent } from './components/offer-map-search-location/offer-map-search-location.component';



@NgModule({
  declarations: [OfferMapPageComponent,OfferMapComponent, OfferMapFilterFormComponent, OfferMapListComponent, OfferMapSearchLocationComponent ],
  imports: [
    CommonModule,
    OfferMapRoutingModule,
    SharedModule
  ]
})
export class OfferMapModule { }
