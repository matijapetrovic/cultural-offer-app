import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { first } from 'rxjs/operators';
import { CulturalOffersService } from '../../cultural-offers.service';


@Component({
    selector: 'app-update-offer',
    templateUrl: './update-offer.component.html',
    styleUrls: ['./update-offer.component.scss']
})
export class UpdateOfferComponent implements OnInit {

    submitted: boolean;
    loading: boolean;
    updateForm: FormGroup;

    //Offer which we update
    offer: any;

    constructor(
        private formBulilder: FormBuilder,
        private config: DynamicDialogConfig,
        private culturalOffersService: CulturalOffersService,
        public ref: DynamicDialogRef
    ) { 
        //deep copying original object
        this.offer = {...this.config.data.offer};
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
        this.updateOffer();
    }

    updateOffer(): void {
        this.culturalOffersService
        .updateCulturalOffer(this.offer)
        .pipe(first())
        .subscribe(() => {
            this.loading = false;
            this.removeFormInputs();
            this.ref.close(this.submitted);
        });
    }

    get f() { return this.updateForm.controls; }
    
   

    invalidFormInputs(): boolean {
        if( this.offer.name === '' || this.offer.name === null || this.nameNotChanged()) {
            return true;
        }
        return false;
    }

    nameNotChanged() {
        return this.offer.name === this.originalOffer.name;
    }

    //Original subcategory so we can know if changes are made
    get originalOffer() { return this.config.data.offer }

    removeFormInputs() {
        this.updateForm.reset();
    }

    errorMessage() {
        return "Name is required!"
    }
}