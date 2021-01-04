import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OfferMapPageComponent } from './pages/offer-map-page/offer-map-page.component';

const routes: Routes = [
  {
    path: '',
    component: OfferMapPageComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OfferMapRoutingModule { }