import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { first } from 'rxjs/operators';
import { SubcategoriesService } from '../../subcategories.service';


@Component({
    selector: 'app-update-subcategory',
    templateUrl: './update-subcategory.component.html',
    styleUrls: ['./update-subcategory.component.scss']
})
export class UpdateSubcategoryComponent implements OnInit {

    submitted: boolean;
    loading: boolean;
    updateForm: FormGroup;

    //Subcategory which we update
    subcategory: any;

    constructor(
        private formBulilder: FormBuilder,
        private config: DynamicDialogConfig,
        private subcategoriesService: SubcategoriesService,
        public ref: DynamicDialogRef
    ) { 
        //deep copying original object
        this.subcategory = {...this.config.data.subcategory};
        this.submitted = false;
        this.loading = false;
    }
    
    ngOnInit(): void {
        this.updateForm = this.formBulilder.group({
            name: ['', Validators.required],
        });
    }

    onSubmit(): void {

        this.submitted = true;

        if(this.invalidFormInputs()) {
            this.removeFormInputs();
            return;
        }

        this.loading = true;
        this.updateSubcategory();
    }

    updateSubcategory(): void {
        this.subcategoriesService
        .updateSubcategory(this.subcategory)
        .pipe(first())
        .subscribe(() => {
            this.loading = false;
            this.removeFormInputs();
            this.ref.close(this.submitted);
        });
    }

    get f() { return this.updateForm.controls; }
    
   

    invalidFormInputs(): boolean {
        if( this.subcategory.name === '' || this.subcategory.name === null || this.nameNotChanged()) {
            return true;
        }
        return false;
    }

    nameNotChanged() {
        return this.subcategory.name === this.originalSubcategory.name;
    }

    //Original subcategory so we can know if changes are made
    get originalSubcategory() { return this.config.data.subcategory }

    removeFormInputs() {
        this.updateForm.reset();
    }

    errorMessage() {
        return "Name is required!"
    }
}