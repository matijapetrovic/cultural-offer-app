import { Component, Input, OnInit } from '@angular/core';
import { News } from '../../news/news';

@Component({
  selector: 'app-offer-news',
  templateUrl: './offer-news.component.html',
  styleUrls: ['./offer-news.component.scss']
})
export class OfferNewsComponent implements OnInit {
  @Input()
  news: News[];
  
  constructor() { }

  ngOnInit(): void {
  }

}
