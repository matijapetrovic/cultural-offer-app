import { Component, OnInit } from '@angular/core';
import { FormGroup} from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { CulturalOffersService } from '../../cultural-offers.service';


@Component({
    selector: 'app-update-offer',
    templateUrl: './update-offer.component.html',
    styleUrls: ['./update-offer.component.scss']
})
export class UpdateOfferComponent{

    //Offer which we update
    offer: any;

    constructor(
        private config: DynamicDialogConfig,
        private culturalOffersService: CulturalOffersService,
        public ref: DynamicDialogRef
    ) { 
        //deep copying original object
        this.offer = {...this.config.data.offer};
    }
}