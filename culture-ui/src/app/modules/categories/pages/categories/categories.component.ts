import { Component, OnInit } from '@angular/core';
import { CategoriesPage, Category } from '../../category';
import { CategoriesService } from 'src/app/modules/categories/categories.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {
  categoriesPage: CategoriesPage;

  private page: number;
  private limit: number = 5;

  display: boolean = false;

  constructor(
    private categoriesService: CategoriesService) {
    this.page = 0;
  }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoriesService.getCategories(this.page, this.limit).subscribe(categories => this.categoriesPage = categories);
  }

  postCategory(): void {
    
  }

  updateCategory(): void {

  }

  deleteCategory(): void {

  }

  getNextCategories() {
    this.page++;
    this.getCategories();
  }

  getPrevCategories() {
    this.page--;
    this.getCategories();
  }
}
