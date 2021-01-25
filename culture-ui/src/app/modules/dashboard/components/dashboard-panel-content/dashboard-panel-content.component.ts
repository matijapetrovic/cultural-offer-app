import { Component, Input, OnInit } from '@angular/core';
import { DashboardService } from '../../dashboard.service';
import { SubscribedOffer, SubscribedOfferPage, SubscribedSubcategory } from '../../subscriptions-details';

@Component({
  selector: 'app-dashboard-panel-content',
  templateUrl: './dashboard-panel-content.component.html',
  styleUrls: ['./dashboard-panel-content.component.scss']
})
export class DashboardPanelContentComponent implements OnInit {
  @Input() subscribedSubcategory: SubscribedSubcategory;

  page: number = 0;
  limit: number = 6;
  loading: boolean = false;

  subscribedOfferPage: SubscribedOfferPage;

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.getSubscribedOffers();
  }

  getSubscribedOffers(): void {
    this.dashboardService.getSubscribedOffers(
      this.subscribedSubcategory.categoryId,
      this.subscribedSubcategory.id,
      this.page,
      this.limit
    )
      .subscribe((offers: SubscribedOfferPage) => this.subscribedOfferPage = offers);
  }

  getPreviousPage(): void {
    this.page--;
    this.getSubscribedOffers();
  }

  getNextPage(): void {
    this.page++;
    this.getSubscribedOffers();
  }
}
