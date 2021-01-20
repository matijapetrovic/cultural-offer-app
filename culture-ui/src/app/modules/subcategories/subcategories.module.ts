import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubcategoriesRoutingModule } from './subcategories-routing.module';
import { SharedModule } from './../../shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';
import { FlexLayoutModule } from '@angular/flex-layout'; 
import { ReactiveFormsModule } from '@angular/forms';
import { SubcategoriesComponent } from './pages/subcategories/subcategories.component';
import { AddSubcategoryComponent } from './pages/add-subcategory/add-subcategory.component';

@NgModule({
  declarations: [ SubcategoriesComponent, AddSubcategoryComponent ],
  imports: [
    CommonModule,
    SubcategoriesRoutingModule,
    SharedModule,
    CoreModule,
    FlexLayoutModule,
    ReactiveFormsModule
  ]
})
export class SubcategoriesModule { }
