import { Component, Input, OnInit } from '@angular/core';
import { CulturalOfferLocation } from 'src/app/modules/cultural-offers/cultural-offer';

@Component({
  selector: 'app-offer-map-list',
  templateUrl: './offer-map-list.component.html',
  styleUrls: ['./offer-map-list.component.scss']
})
export class OfferMapListComponent implements OnInit {

  @Input()
  culturalOffers: CulturalOfferLocation[];

  constructor() { }

  ngOnInit(): void {
  }

}
