import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { first } from 'rxjs/internal/operators/first';
import { CulturalOfferView } from '../../cultural-offer';
import { CulturalOffersService } from '../../cultural-offers.service';


@Component({
    selector: 'app-update-offer',
    templateUrl: './update-offer.component.html',
    styleUrls: ['./update-offer.component.scss']
})
export class UpdateOfferComponent{

    //Offer which we update
    offer: CulturalOfferView;

    loading:boolean;

    constructor(
        private config: DynamicDialogConfig,
        private culturalOffersService: CulturalOffersService,
        public ref: DynamicDialogRef
    ) { 
        //deep copying original object
        this.offer = {...this.config.data.offer};
        this.loading = false;
    }

    updateOffer(offer:any) {

        this.loading = true;

        this.culturalOffersService.updateCulturalOffer(offer)
        .pipe(first())
        .subscribe(() => {
            this.loading = false;
            this.ref.close(true);
        });
    }
}