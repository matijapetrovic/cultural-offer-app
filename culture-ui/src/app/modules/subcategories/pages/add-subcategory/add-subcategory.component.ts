import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SubcategoriesService } from 'src/app/modules/subcategories/subcategories.service';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { Subcategory } from 'src/app/modules/subcategories/subcategory';
import { first } from 'rxjs/operators';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';


@Component({
    selector: 'app-add-subcategory',
    templateUrl: './add-subcategory.component.html',
    styleUrls: ['./add-subcategory.component.scss']
})
export class AddSubcategoryComponent implements OnInit {

    addForm: FormGroup;
    loading: boolean;
    submitted: boolean;

    // Category to which we add subcategory
    category: any;

    constructor(
        private subcategoryService: SubcategoriesService,
        private formBuilder: FormBuilder,
        public ref: DynamicDialogRef,
        public config: DynamicDialogConfig,
    ) {
        this.category = this.config.data.category;
        this.loading = false;
        this.submitted = false;
    }

    ngOnInit(): void {
        this.addForm = this.formBuilder.group({
            name: ['', Validators.required],
        });
    }

    onSubmit(): void {
        this.submitted = true;

        if (this.invalidFormInputs()) {
            this.removeFormInputs();
            return;
        }
        this.loading = true;
        this.addSubcategory();
    }

    addSubcategory() {
        const subcategory: Subcategory = {id: null, categoryId: this.category.id, name: this.f.name.value };
        this.subcategoryService.addSubcategory(subcategory)
        .pipe(first())
        .subscribe(() => {
            this.loading = false;
            this.removeFormInputs();
            this.ref.close(this.submitted);
        });
    }

    get f() { return this.addForm.controls; }

    invalidFormInputs(): boolean {
        if (this.f.name.value === '' || this.f.name.value === null) {
            return true;
        }
        return false;
    }

    removeFormInputs() {
        this.addForm.reset();
    }

    errorMessage() {
        return 'Name is required!';
    }

}
