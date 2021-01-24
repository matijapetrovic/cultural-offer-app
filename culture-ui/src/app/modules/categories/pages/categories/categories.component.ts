import { Component, OnInit } from '@angular/core';
import { CategoriesPage } from '../../category';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { DialogService } from 'primeng/dynamicdialog';
import { AddCategoryComponent } from '../add-category/add-category.component';
import { MessageService } from 'primeng/api';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmationService } from 'primeng/api';
import { UpdateCategoryComponent } from '../update-category/update-category.component';


@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss'],
  providers: [DialogService, MessageService]
})
export class CategoriesComponent implements OnInit {
  categoriesPage: CategoriesPage;

  private page: number;
  private limit = 5;
  public ref: DynamicDialogRef;

  constructor(
    private categoriesService: CategoriesService,
    public dialogService: DialogService,
    public messageService: MessageService,
    public confirmationService: ConfirmationService
  ) {
    this.page = 0;
  }

  showAddForm(): void {
    this.ref = this.dialogService.open(AddCategoryComponent, {
      header: 'Add category',
      width: '30%',
      dismissableMask: true
    });

    this.ref.onClose.subscribe((submitted) => {
      if (submitted) {
        this.getCategories();
        this.messageService.add({
          severity: 'success', summary: 'Category adding successful', detail: 'You have successfully added category' });
        setTimeout(() => this.messageService.clear(), 2000);
      }
    });
  }

  showUpdateForm(category: any): void {
    this.ref = this.dialogService.open(UpdateCategoryComponent, {
      data: {
        category
      },
      header: 'Update category',
      width: '30%',
      dismissableMask: true
    });

    this.ref.onClose.subscribe((submitted) => {
      if (submitted) {
        this.getCategories();
        this.messageService.add({ severity: 'success', summary: 'Category updating successful', detail: 'You have successfully updated category' });
        setTimeout(() => this.messageService.clear(), 2000);
      }
    });
  }

  showDeleteForm(id: number): void {
    this.confirmationService.confirm({
      message: 'Do you want to delete this category?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'You have successfuly deleted category!'});
        this.deleteCategory(id);
      },
      reject: () => {
      }
    });
  }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoriesService.getCategories(this.page, this.limit).subscribe(categories => this.categoriesPage = categories);
  }

  deleteCategory(id: number): void {
    this.categoriesService.deleteCategory(id)
    .pipe()
    .subscribe(
      () => {
        this.getCategories();
      });
  }

  getNextCategories(): void {
    this.page++;
    this.getCategories();
  }

  getPrevCategories(): void {
    this.page--;
    this.getCategories();
  }
}
