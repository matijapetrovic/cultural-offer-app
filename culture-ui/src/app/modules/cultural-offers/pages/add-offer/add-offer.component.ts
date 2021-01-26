import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { first } from 'rxjs/operators';
import { CulturalOffersService } from 'src/app/modules/cultural-offers/cultural-offers.service'



@Component({
    selector: 'app-add-offer',
    templateUrl: './add-offer.component.html',
    styleUrls: ['./add-offer.component.scss']
})
export class AddOfferComponent{

    loading: boolean;

    constructor(
        private culturalOffersService: CulturalOffersService,
        public ref: DynamicDialogRef,
    ) { 
        this.loading = false;
    }

    postOffer(offer:any): void {
        this.loading = true;

        this.culturalOffersService.addCulturalOffer(offer)
        .pipe(first())
        .subscribe(() => {
            this.loading = false;
            this.ref.close(true);
        });
    }
}