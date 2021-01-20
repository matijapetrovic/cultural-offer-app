import { Component, OnInit } from '@angular/core'
import { SubcategoriesPage } from '../../subcategory'
import { SubcategoriesService } from '../../subcategories.service'
import { CategoriesService } from 'src/app/modules/categories/categories.service'
import { Category } from 'src/app/modules/categories/category';

@Component({
    selector: 'app-subcategories',
    templateUrl: './subcategories.component.html',
    styleUrls: ['./subcategories.component.scss']   
})
export class SubcategoriesComponent implements OnInit {

    subcategoriesPage:SubcategoriesPage;
    categories: Category[];

    private page: number;
    private limit: number;

    constructor(
        private subcategoriesService:SubcategoriesService,
        private categoriesService:CategoriesService
    ) {
        this.page = 0;
        this.limit = 5;
    }

    ngOnInit(): void {
        this.getCategories();
    }

    getCategories(): void {
        this.categoriesService
        .getCategoryNames()
        .subscribe(categories => this.categories = categories);
    }

    getSubcategories(category:any): void {
        this.subcategoriesService
        .getSubcategories(category.id, this.page, this.limit)
        .subscribe(subcategories => this.subcategoriesPage = subcategories);
    }

    showAddForm(): void {

    }

    showUpdateForm(subcategory:any): void {

    }

    showDeleteForm(id:number): void {
        
    }

    emitCategorySelected(category:any): void {

    }
    
}