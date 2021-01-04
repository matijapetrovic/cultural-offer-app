import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './_helpers/auth.guard';
import { Role } from './_models/role';

const routes: Routes = [
  {
    path: 'cultural-offers',
    loadChildren: () => import('./modules/cultural-offers/cultural-offers.module').then(m => m.CulturalOffersModule),
  },
  {
    path: 'register',
    loadChildren: () => import('./registration/registration.module').then(m => m.RegistrationModule),
  },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
  },
  {
    path: '',
    redirectTo: 'map',
    pathMatch: "full",
  },
  {
    path: 'map',
    loadChildren: () => import('./modules/offer-map/offer-map.module').then(m => m.OfferMapModule)
  },
  {
    path: 'categories',
    loadChildren: () => import('./modules/categories/categories.module').then(m => m.CategoriesModule),
    canActivate: [AuthGuard],
    data: { roles: [Role.Admin] }
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
