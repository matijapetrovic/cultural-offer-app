import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SubcategoriesComponent } from './pages/subcategories/subcategories.component';

const routes: Routes = [
    {
        path: '',
        component: SubcategoriesComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SubcategoriesRoutingModule { }
