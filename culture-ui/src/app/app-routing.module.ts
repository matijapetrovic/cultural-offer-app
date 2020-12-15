import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    //path: 'cultural-offers',
    path: '',
    loadChildren: () => import('./modules/cultural-offers/cultural-offers.module').then(m => m.CulturalOffersModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
