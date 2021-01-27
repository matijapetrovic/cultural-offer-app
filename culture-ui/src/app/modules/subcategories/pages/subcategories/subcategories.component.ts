import { Component, OnInit } from '@angular/core';
import { SubcategoriesPage } from '../../subcategory';
import { SubcategoriesService } from '../../subcategories.service';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { Category } from 'src/app/modules/categories/category';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { DialogService } from 'primeng/dynamicdialog';
import { AddSubcategoryComponent } from 'src/app/modules/subcategories/pages/add-subcategory/add-subcategory.component';
import { UpdateSubcategoryComponent } from '../update-subcategory/update-subcategory.component';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
    selector: 'app-subcategories',
    templateUrl: './subcategories.component.html',
    styleUrls: ['./subcategories.component.scss'],
    providers: [DialogService]
})
export class SubcategoriesComponent implements OnInit {

    subcategoriesPage: SubcategoriesPage;
    categories: Category[];
    public ref: DynamicDialogRef;

    private page: number;
    private limit: number;

    // Temproary selected category
    public tempCategory: Category;

    constructor(
        private subcategoriesService: SubcategoriesService,
        private categoriesService: CategoriesService,
        public dialogService: DialogService,
        public messageService: MessageService,
        public confirmationService: ConfirmationService
    ) {
        this.page = 0;
        this.limit = 5;
        this.tempCategory = null;
    }

    showAddForm(): void {

        this.ref = this.dialogService.open(
            AddSubcategoryComponent,
            {
                data: {
                    category: this.tempCategory
                },
                header: 'Add category',
                width: '30%',
                dismissableMask: true
            }
        );

        this.ref.onClose.subscribe(submited => {
            if (submited) {
                this.getSubcategories();
                this.messageService.add({
                    severity: 'success', summary: 'Subcategory adding successful', detail: 'You have successfully added subcategory' });
                  setTimeout(() => this.messageService.clear(), 2000);
            }
        });

    }

    showUpdateForm(subcategory: any): void {

        this.ref = this.dialogService.open(
            UpdateSubcategoryComponent,
            {
                data: {
                    subcategory
                },
                header: 'Update category',
                width: '30%',
                dismissableMask: true
            }
        );

        this.ref.onClose.subscribe(submited => {
            if (submited) {
                this.getSubcategories();
                this.messageService.add({ severity: 'success', summary: 'Subcategory updating successful', detail: 'You have successfully updated subcategory' });
                setTimeout(() => this.messageService.clear(), 2000);
            }
        });

    }

    showDeleteForm(subcategory: any): void {
        this.confirmationService.confirm({
            message: 'Do you want to delete this subcategory?',
            header: 'Delete Confirmation',
            icon: 'pi pi-info-circle',
            accept: () => {
              this.messageService.add({ severity: 'success', summary: 'Confirmed', detail: 'You have successfuly deleted subcategory!' });
              this.deleteSubcategory(subcategory);
            },
            reject: () => {
            }
          });
    }

    ngOnInit(): void {
        this.getCategories();
    }

    deleteSubcategory(subcategory: any): void {
        this.subcategoriesService.deleteSubcategory(subcategory)
        .pipe()
        .subscribe(
            () => {
                this.getSubcategories();
        });
    }

    getCategories(): void {
        this.categoriesService
        .getCategoryNames()
        .subscribe(categories => this.categories = categories);
    }

    categoryChanged(category: any): void {

        // Each time we change selected category we change tempChategory, too
        this.tempCategory = category;

        this.getSubcategories();
    }

    getSubcategories(): void {
        this.subcategoriesService
        .getSubcategories(this.tempCategory.id, this.page, this.limit)
        .subscribe(subcategories => this.subcategoriesPage = subcategories);
    }

    getNextSubcategories() {
        this.page++;
        this.getSubcategories();
    }

    getPrevSubcategories() {
        this.page--;
        this.getSubcategories();
    }
}
