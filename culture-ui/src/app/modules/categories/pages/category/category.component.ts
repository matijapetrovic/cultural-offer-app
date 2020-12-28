import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Category } from '../../categories';
import { CategoriesService } from 'src/app/modules/categories/categories.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  category: Category;

  private categoryId: number;

  constructor(
    private categoriesService: CategoriesService,
    private route: ActivatedRoute) 
  {
    
  }

  ngOnInit(): void {
    this.categoryId = +this.route.snapshot.paramMap.get('id');
    this.getCategory();
  }

  getCategory(): void {
    this.categoriesService.getCategory(this.categoryId).subscribe(category => this.category = category);
  }
}
