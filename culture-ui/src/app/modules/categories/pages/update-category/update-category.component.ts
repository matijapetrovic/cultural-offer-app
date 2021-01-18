import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { CategoriesService } from '../../categories.service';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { Category } from '../../category';


@Component({
  selector: 'app-update-category',
  templateUrl: './update-category.component.html',
  styleUrls: ['./update-category.component.scss']
})
export class UpdateCategoryComponent implements OnInit {

  addForm: FormGroup;
  loading = false;
  submitted = false;
  category: any;
  name: string;

  constructor(
    private categoryService: CategoriesService,
    private formBuilder: FormBuilder,
    public config: DynamicDialogConfig,
    public ref: DynamicDialogRef
  ) {
    this.category = this.config.data.category;
    this.name = this.category.name;
  }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      name: ['', Validators.required],
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.invalidFormInputs()) {
      return;
    }
    this.loading = true;
    this.updateCategory();
    this.ref.close(this.submitted);
  }

  updateCategory() {
    let updatedCategory: Category = { id: this.category.id, name: this.name };
    this.categoryService.updateCategory(updatedCategory)
      .pipe(first())
      .subscribe(
        () => {
          this.loading = false;
        });
  }

  get f() { return this.addForm.controls; }

  invalidFormInputs() {
    if (this.f.name.value === '' || this.f.name.value === null || this.areNamesSame()) {
      return true;
    }
    return false;
  }

  areNamesSame() {
    return this.category.name === this.name;
  }

  errorMessage() {
    return "Name is required!"
  }
}