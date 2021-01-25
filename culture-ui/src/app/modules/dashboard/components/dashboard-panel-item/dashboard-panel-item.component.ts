import { Component, Input, OnInit } from '@angular/core';
import { SubscribedOffer } from '../../subscriptions-details';

@Component({
  selector: 'app-dashboard-panel-item',
  templateUrl: './dashboard-panel-item.component.html',
  styleUrls: ['./dashboard-panel-item.component.scss']
})
export class DashboardPanelItemComponent implements OnInit {
  @Input() subscribedOffer: SubscribedOffer;

  constructor() { }

  ngOnInit(): void {
  }

}
