import { Component, OnInit } from '@angular/core'
import { CulturalOffersPage } from '../../cultural-offer'
import { CulturalOffersService } from '../../cultural-offers.service'
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { DialogService } from 'primeng/dynamicdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AddOfferComponent } from '../add-offer/add-offer.component';
import { UpdateOfferComponent } from '../update-offer/update-offer.component';
import {Router} from "@angular/router"

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
        public confirmationService:ConfirmationService,
        public messageService: MessageService,
        private router: Router
    ) {
        this.page = 0;
        this.limit = 5;;
    }

    showAddForm(): void {

        this.ref = this.dialogService.open(
            AddOfferComponent,
            {
                header: 'Add Cultural Offer',
                width: '55%',
                dismissableMask: true
            }
        );

        this.ref.onClose.subscribe(submited => {
            if(submited) {
                this.getCulturalOffers();
                this.messageService.add({
                    severity: 'success', summary: 'Cultural offer added successful', detail: 'You have successfully added cultural offer' });
                  setTimeout(() => this.messageService.clear(), 2000);
            }
        })

    }

    showNews(offer:any) {
        this.router.navigate([`/cultural-offers/${offer.id}/news`]);
    }

    showUpdateForm(offer:any): void {

        this.ref = this.dialogService.open(
            UpdateOfferComponent,
            {
                data: {
                    offer: offer
                },
                header: 'Update Cultural Offer',
                width: '55%',
                dismissableMask: true
            }
        );

        this.ref.onClose.subscribe(submited => {
            if(submited) {
                this.getCulturalOffers();
                this.messageService.add({ severity: 'success', summary: 'Cultural offer updated successful', detail: 'You have successfully updated cultural offer' });
                setTimeout(() => this.messageService.clear(), 2000);
            }
        })

    }

    showDeleteForm(offer:any): void {
        this.confirmationService.confirm({
            message: 'Do you want to delete this cultural offer?',
            header: 'Delete Confirmation',
            icon: 'pi pi-info-circle',
            accept: () => {
              this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'You have successfuly deleted cultural offer!' });
              this.deleteCulturalOffer(offer);
            },
            reject: () => {
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