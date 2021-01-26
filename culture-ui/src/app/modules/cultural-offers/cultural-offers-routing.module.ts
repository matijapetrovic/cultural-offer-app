import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';
import { CulturalOffersComponent } from './pages/cultural-offers/cultural-offers.component';

const routes: Routes = [
  {
    path: ':id',
    component: CulturalOfferComponent
  },
  {
    path: '',
    component: CulturalOffersComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CulturalOffersRoutingModule { }
