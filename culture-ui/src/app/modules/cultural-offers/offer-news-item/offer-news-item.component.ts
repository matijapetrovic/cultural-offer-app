import { Component, Input, OnInit } from '@angular/core';
import { News } from '../../news/news';

@Component({
  selector: 'app-offer-news-item',
  templateUrl: './offer-news-item.component.html',
  styleUrls: ['./offer-news-item.component.scss']
})
export class OfferNewsItemComponent implements OnInit {
  @Input()
  newsItem: News;
  
  constructor() { }

  ngOnInit(): void {
  }

}
