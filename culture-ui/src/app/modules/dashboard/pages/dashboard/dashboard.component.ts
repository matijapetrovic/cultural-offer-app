import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../../dashboard.service';
import { SubscribedSubcategoriesPage } from '../../subscriptions-details';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  subSubCatPage: SubscribedSubcategoriesPage;

  page = 0;
  private limit = 5;

  constructor(private dashboardService: DashboardService) {
  }

  ngOnInit(): void {
    this.getSubscribedSubcategories();
  }

  getSubscribedSubcategories(): void {
    this.dashboardService.getSubscribedSubcategories(this.page, this.limit)
      .subscribe((subcategories: SubscribedSubcategoriesPage) => this.subSubCatPage = subcategories);
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
