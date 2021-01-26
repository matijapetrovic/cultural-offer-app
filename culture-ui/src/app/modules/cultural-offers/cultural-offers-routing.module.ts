import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';

const routes: Routes = [
  {
    path: ':id',
    component: CulturalOfferComponent
  },
  {
    path: ':offerId/news',
    loadChildren: () => import('./../news/news.module').then(m => m.NewsModule)
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CulturalOffersRoutingModule { }
