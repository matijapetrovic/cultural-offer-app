import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { Role } from '../authentication/role';
import { CulturalOfferComponent } from './pages/cultural-offer/cultural-offer.component';
import { CulturalOffersComponent } from './pages/cultural-offers/cultural-offers.component';

const routes: Routes = [
  {
    path: ':id',
    component: CulturalOfferComponent
  },
  {
    path: ':offerId/news',
    loadChildren: () => import('./../news/news.module').then(m => m.NewsModule),
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN] }
  },
  {
    path: '',
    component: CulturalOffersComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN] }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CulturalOffersRoutingModule { }
