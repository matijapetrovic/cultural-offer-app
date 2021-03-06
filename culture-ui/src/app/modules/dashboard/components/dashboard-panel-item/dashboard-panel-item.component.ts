import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CulturalOffersService } from 'src/app/modules/cultural-offers/cultural-offers.service';
import { SubscribedOffer } from '../../subscriptions-details';

@Component({
  selector: 'app-dashboard-panel-item',
  templateUrl: './dashboard-panel-item.component.html',
  styleUrls: ['./dashboard-panel-item.component.scss']
})
export class DashboardPanelItemComponent implements OnInit {
  @Input() subscribedOffer: SubscribedOffer;

  @Output() unsubscribedEvent: EventEmitter<void> = new EventEmitter<void>();

  constructor(
    private router: Router,
    private offersService: CulturalOffersService,
    private messageService: MessageService,
  ) { }

  ngOnInit(): void {
    // this.unsubscribedEvent = new EventEmitter<void>();
  }

  redirectToOfferPage(): void {
    this.router.navigate([`/cultural-offers/${this.subscribedOffer.id}`]);
  }

  confirmUnsubscribe(): void {
    this.unsubscribe();
  }

  unsubscribe(): void {
    this.offersService.unsubscribeFromCulturalOffer(this.subscribedOffer.id)
      .subscribe(() => {
        this.unsubscribedEvent.emit();
        this.messageService.add({ severity: 'success', summary: 'Unsubscription successful', detail: 'You have successfully unsubscribed from chosen cultural offer\'s newsletter' });
        setTimeout(() => this.messageService, 5000);
      });

  }
}
