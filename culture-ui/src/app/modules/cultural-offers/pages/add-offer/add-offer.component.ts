import { Component } from '@angular/core';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { CulturalOffersService } from 'src/app/modules/cultural-offers/cultural-offers.service';



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

    postOffer(offer: any): void {
        this.loading = true;

        this.culturalOffersService.addCulturalOffer(offer)
        .subscribe(() => {
            this.loading = false;
            this.ref.close(true);
        });
    }
}
