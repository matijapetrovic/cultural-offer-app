import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { CategoriesService } from '../../categories.service';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { Category } from '../../category';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.scss']
})
export class AddCategoryComponent implements OnInit {

  addForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private categoryService: CategoriesService,
    private formBuilder: FormBuilder,
    public ref: DynamicDialogRef
  ) { }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      name: ['', Validators.required],
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.invalidFormInputs()) {
      this.removeFormInputs();
      return;
    }
    this.loading = true;
    this.addCategory();
  }

  addCategory() {
    let category: Category = { id: null, name: this.f.name.value };
    this.categoryService.addCategory(category)
      .subscribe(
        () => {
          this.loading = false;
          this.removeFormInputs();
          this.ref.close(this.submitted);
        });
  }

  get f() { return this.addForm.controls; }

  removeFormInputs() {
    this.addForm.reset();
  }

  invalidFormInputs() {
    if (this.f.name.value === '' || this.f.name.value === null) {
      return true;
    }
    return false;
  }

  errorMessage() {
    return "Name is required!"
  }
}