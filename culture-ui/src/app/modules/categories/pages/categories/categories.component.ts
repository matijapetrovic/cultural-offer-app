import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CategoriesPage } from '../../category';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { DialogService } from 'primeng/dynamicdialog';
import { AddCategoryComponent } from '../add-category/add-category.component';
import { MessageService } from 'primeng/api';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmationService } from 'primeng/api'
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
  private limit: number = 5;
  public ref: DynamicDialogRef;

  private tableChanged = false;

  constructor(
    private categoriesService: CategoriesService,
    public dialogService: DialogService,
    public messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
    this.page = 0;
  }

  showAddForm() {
    this.ref = this.dialogService.open(AddCategoryComponent, {
      header: 'Add category',
      width: '30%',
      dismissableMask: true
    });

    this.ref.onDestroy.subscribe(() => {
      // this.messageService.add({ severity: 'info', summary: 'Category updated', detail: 'Name:' + category.name });
      if(this.tableChanged) {

        console.log('usao');
        this.getCategories();
      }
    });

    this.ref.onClose.subscribe((submitted: boolean) => {
      this.tableChanged = submitted;
    });
  }

  showUpdateForm(category: any) {
    this.ref = this.dialogService.open(UpdateCategoryComponent, {
      data: {
        category: category
      },
      header: 'Update category',
      width: '30%',
      dismissableMask: true
    });

    this.ref.onDestroy.subscribe(() => {
      // this.messageService.add({ severity: 'info', summary: 'Category updated', detail: 'Name:' + category.name });
      if (this.tableChanged) {
        this.getCategories();
      }
    });
  }

  showDeleteForm(id: number) {
    this.confirmationService.confirm({
      message: 'Do you want to delete this category?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        //this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'Record deleted' });
        this.deleteCategory(id);
        this.getCategories();
      },
      reject: () => {
        //this.messageService.add({ severity: 'info', summary: 'Rejected', detail: 'You have rejected' });
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
    this.categoriesService.deleteCategory(id).subscribe();
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
