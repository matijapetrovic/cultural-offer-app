import { Component, Input, OnInit } from '@angular/core';
import { DashboardService } from '../../dashboard.service';
import { SubscribedOffer, SubscribedSubcategory } from '../../subscriptions-details';

@Component({
  selector: 'app-dashboard-panel-content',
  templateUrl: './dashboard-panel-content.component.html',
  styleUrls: ['./dashboard-panel-content.component.scss']
})
export class DashboardPanelContentComponent implements OnInit {
  @Input() subscribedSubcategory: SubscribedSubcategory;

  subscribedOffers: SubscribedOffer[];

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.getSubscribedOffers();
  }

  getSubscribedOffers(): void {
    this.dashboardService.getSubscribedOffers(this.subscribedSubcategory.categoryId, this.subscribedSubcategory.id)
      .subscribe((offers: SubscribedOffer[]) => this.subscribedOffers = offers);
  }
}
