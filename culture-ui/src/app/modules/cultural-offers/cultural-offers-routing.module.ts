import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CulturalOfferMapComponent } from './pages/cultural-offer-map/cultural-offer-map.component';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';

const routes: Routes = [
  {
    path: '',
    component: CulturalOfferMapComponent
  },
  {
    path: ':id',
    component: CulturalOfferComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CulturalOffersRoutingModule { }
