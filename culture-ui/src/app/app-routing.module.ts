import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    //path: 'cultural-offers',
    path: 'cultural-offers',
    loadChildren: () => import('./modules/cultural-offers/cultural-offers.module').then(m => m.CulturalOffersModule),
  },
  {
    path: 'categories',
    loadChildren: () => import('./modules/categories/categories.module').then(m => m.CategoriesModule),
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
