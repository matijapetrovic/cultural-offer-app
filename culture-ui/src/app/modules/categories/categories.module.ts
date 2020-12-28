import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout'; 

import { SharedModule } from './../../shared/shared.module';
import { CategoryComponent } from './pages/category/category.component';
import { CategoriesRoutingModule } from './categories-routing.module';
import { CategoriesComponent } from './pages/categories/categories.component';

@NgModule({
  declarations: [CategoryComponent, CategoriesComponent],
  imports: [
    CommonModule,
    CategoriesRoutingModule,
    SharedModule,
    FlexLayoutModule
  ]
})
export class CategoriesModule { }
