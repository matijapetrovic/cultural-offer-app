import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CulturalOffersService } from 'src/app/modules/cultural-offers/cultural-offers.service'
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { first } from 'rxjs/operators';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';
import { CulturalOffer } from 'src/app/modules/cultural-offers/cultural-offer'


@Component({
    selector: 'app-add-offer',
    templateUrl: './add-offer.component.html',
    styleUrls: ['./add-offer.component.scss']
})
export class AddOfferComponent implements OnInit {

    addForm: FormGroup;
    loading: boolean;
    submitted: boolean;

    constructor(
        private culturalOffersService: CulturalOffersService,
        private formBuilder: FormBuilder,
        public ref: DynamicDialogRef,
        public config: DynamicDialogConfig,
    ) { 
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

        if(this.invalidFormInputs()) {
            this.removeFormInputs();
            return;
        }
        this.loading = true;
        this.addCulturalOffer();
    }

    addCulturalOffer() {
        let culturalOffer:CulturalOffer = null;
        this.culturalOffersService.addCulturalOffer(culturalOffer)
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
        return "Name is required!"
    }

}