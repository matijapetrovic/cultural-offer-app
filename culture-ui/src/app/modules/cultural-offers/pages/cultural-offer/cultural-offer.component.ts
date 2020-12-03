import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { CulturalOffersService } from 'src/app/modules/cultural-offers/cultural-offers.service';
import { CulturalOffer } from '../../cultural-offer';

@Component({
  selector: 'app-cultural-offer',
  templateUrl: './cultural-offer.component.html',
  styleUrls: ['./cultural-offer.component.scss']
})
export class CulturalOfferComponent implements OnInit {
  culturalOffer: CulturalOffer;

  constructor(
    private culturalOfferService : CulturalOffersService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.getCulturalOffer(+this.route.snapshot.paramMap.get('id'));
  }


  getCulturalOffer(id: number): void {
    this.culturalOfferService.getCulturalOffer(id).subscribe(culturalOffer => this.culturalOffer = culturalOffer);
  }
}
