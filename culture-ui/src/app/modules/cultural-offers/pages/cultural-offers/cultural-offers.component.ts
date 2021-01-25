import { Component, OnInit } from '@angular/core'
import { CulturalOffersPage } from '../../cultural-offer'
import { CulturalOffersService } from '../../cultural-offers.service'
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { DialogService } from 'primeng/dynamicdialog';
import { ConfirmationService } from 'primeng/api';
import { AddOfferComponent } from '../add-offer/add-offer.component';
import { UpdateOfferComponent } from '../update-offer/update-offer.component';

@Component({
    selector: 'app-cultural-offers',
    templateUrl: './cultural-offers.component.html',
    styleUrls: ['./cultural-offers.component.scss'],
    providers: [DialogService] 
})
export class CulturalOffersComponent implements OnInit {

    culturalOffersPage:CulturalOffersPage;
    public ref: DynamicDialogRef;

    private page: number;
    private limit: number;

    constructor(
        private culturalOffersService:CulturalOffersService,
        public dialogService:DialogService,
        private confirmationService:ConfirmationService
    ) {
        this.page = 0;
        this.limit = 5;;
    }

    showAddForm(): void {

        this.ref = this.dialogService.open(
            AddOfferComponent,
            {
                header: 'Add Cultural Offer',
                width: '35%',
                dismissableMask: true
            }
        );

        this.ref.onClose.subscribe(submited => {
            if(submited) {
                this.getCulturalOffers();
            }
        })

    }

    showUpdateForm(offer:any): void {

        this.ref = this.dialogService.open(
            UpdateOfferComponent,
            {
                data: {
                    offer: offer
                },
                header: 'Update Cultural Offer',
                width: '35%',
                dismissableMask: true
            }
        );

        this.ref.onClose.subscribe(submited => {
            if(submited) {
                this.getCulturalOffers();
            }
        })

    }

    showDeleteForm(offer:any): void {
        this.confirmationService.confirm({
            message: 'Do you want to delete this cultural offer?',
            header: 'Delete Confirmation',
            icon: 'pi pi-info-circle',
            accept: () => {
              //this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'Record deleted' });
              this.deleteCulturalOffer(offer);
            },
            reject: () => {
              //this.messageService.add({ severity: 'info', summary: 'Rejected', detail: 'You have rejected' });
            }
          });
    }

    ngOnInit(): void {
        this.getCulturalOffers();
    }

    deleteCulturalOffer(culturalOffer:any):void {
        this.culturalOffersService.deleteCulturalOffer(culturalOffer)
        .pipe()
        .subscribe(
            () => {
                this.getCulturalOffers();
        });
    }

    getCulturalOffers(): void {
        this.culturalOffersService
        .getCulturaOffers(this.page, this.limit)
        .subscribe(culturalOffers => this.culturalOffersPage = culturalOffers);        
    }

    getNextCulturalOffers() {
        this.page++;
        this.getCulturalOffers();
    }
    
    getPrevCulturalOffers() {
        this.page--;
        this.getCulturalOffers();
    }
}