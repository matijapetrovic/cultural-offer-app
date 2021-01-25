import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../../dashboard.service';
import { SubscriptionsSubcategoriesPage } from '../../subscriptions-details';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  subSubCatPage: SubscriptionsSubcategoriesPage;

  private page: number;
  private limit: number = 5;

  constructor(private dashboardService: DashboardService) {
  }

  ngOnInit(): void {
    this.page = 0;
    this.getSubscribedSubcategories();
  }

  getSubscribedSubcategories(): void {
    this.dashboardService.getSubscribedSubcategories(this.page, this.limit)
      .subscribe(subcategories => this.subSubCatPage = subcategories);
  }

  getPreviousPage(): void {
    this.page--;
    this.getSubscribedSubcategories();
  }

  getNextPage(): void {
    this.page++;
    this.getSubscribedSubcategories();
  }
}
