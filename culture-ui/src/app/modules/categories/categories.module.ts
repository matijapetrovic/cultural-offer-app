import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout'; 

import { SharedModule } from './../../shared/shared.module';
import { CategoriesRoutingModule } from './categories-routing.module';
import { CategoriesComponent } from './pages/categories/categories.component';
import { AddCategoryComponent } from './pages/add-category/add-category.component';
import { CoreModule } from 'src/app/core/core.module';
import { ReactiveFormsModule } from '@angular/forms';
import { UpdateCategoryComponent } from './pages/update-category/update-category.component';

@NgModule({
  declarations: [CategoriesComponent, AddCategoryComponent, UpdateCategoryComponent],
  imports: [
    CommonModule,
    CategoriesRoutingModule,
    SharedModule,
    FlexLayoutModule,
    CoreModule,
    ReactiveFormsModule
  ]
})
export class CategoriesModule { }
