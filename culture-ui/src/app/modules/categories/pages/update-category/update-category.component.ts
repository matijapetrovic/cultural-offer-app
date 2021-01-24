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

  updateForm: FormGroup;
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
    this.updateForm = this.formBuilder.group({
      name: ['', Validators.required],
    });
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.invalidFormInputs()) {
      return;
    }
    this.loading = true;
    this.updateCategory();
  }

  updateCategory(): void {
    const updatedCategory: Category = { id: this.category.id, name: this.name };
    this.categoryService.updateCategory(updatedCategory)
      .subscribe(
        () => {
          this.loading = false;
          this.ref.close(this.submitted);
        });
  }

  get f(): any { return this.updateForm.controls; }

  invalidFormInputs(): boolean {
    if (this.f.name.value === '' || this.f.name.value === null || this.areNamesSame()) {
      return true;
    }
    return false;
  }

  areNamesSame(): boolean {
    return this.category.name === this.name;
  }

  errorMessage(): string {
    return 'Name is required!';
  }
}
