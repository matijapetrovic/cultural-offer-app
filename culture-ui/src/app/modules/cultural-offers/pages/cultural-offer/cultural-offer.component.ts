import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { Role } from 'src/app/modules/authentication/role';
import { User } from 'src/app/modules/authentication/user';
import { CulturalOffersService } from 'src/app/modules/cultural-offers/cultural-offers.service';
import { NewsPage } from 'src/app/modules/news/news';
import { NewsService } from 'src/app/modules/news/news.service';
import { ReviewPage } from 'src/app/modules/reviews/review';
import { ReviewsService } from 'src/app/modules/reviews/reviews.service';
import { CulturalOffer } from '../../cultural-offer';

@Component({
  selector: 'app-cultural-offer',
  templateUrl: './cultural-offer.component.html',
  styleUrls: ['./cultural-offer.component.scss']
})
export class CulturalOfferComponent implements OnInit {
  culturalOffer: CulturalOffer;
  subscribed: boolean = null;
  reviewPage: ReviewPage;
  newsPage: NewsPage;

  private culturalOfferId: number;

  private currentReviewsPage: number;
  private reviewsLimit: number = 3;

  private currentNewsPage: number;
  private newsLimit: number = 3;

  mapOptions: any;
  mapOverlays: any;
  mapInfoWindow: any;

  constructor(
    private culturalOffersService : CulturalOffersService,
    private reviewsService: ReviewsService,
    private newsService: NewsService,
    private route: ActivatedRoute,
    private confirmationService: ConfirmationService,
    private authenticationService: AuthenticationService
  ) {
    this.currentReviewsPage = 0;
    this.currentNewsPage = 0;
  }

  ngOnInit(): void {
    this.culturalOfferId = +this.route.snapshot.paramMap.get('id');
    this.getCulturalOffer();
    this.getReviews();
    this.getNews();
    this.mapInfoWindow = new google.maps.InfoWindow();
    this.authenticationService.currentUser.subscribe((user) => this.updateSubscribed(user));
  }

  updateSubscribed(user: User) {
    if (user == null || user.role == Role.Admin) {
      this.subscribed = null;
      return;
    }
    this.getSubscribed();
  }

  getCulturalOffer(): void {
    this.culturalOffersService.getCulturalOffer(this.culturalOfferId).subscribe(culturalOffer =>  {
      this.culturalOffer = culturalOffer;
      this.mapOptions = {
        center: {lat: culturalOffer.latitude, lng: culturalOffer.longitude},
        zoom: 9
      };
      this.mapOverlays = [
        new google.maps.Marker({
          position: {lat: culturalOffer.latitude, lng:culturalOffer.longitude },
          title: culturalOffer.name
        })
      ]
    });
  }

  getReviews(): void {
    this.reviewsService.getReviews(this.culturalOfferId, this.currentReviewsPage, this.reviewsLimit).subscribe(reviewPage => this.reviewPage = reviewPage);
  }

  getNews(): void {
    this.newsService.getNews(this.culturalOfferId, this.currentNewsPage, this.newsLimit).subscribe(newsPage => this.newsPage = newsPage);
  }

  getNextReviews(): void {
    this.currentReviewsPage++;
    this.getReviews();
  }

  getPrevReviews(): void {
    this.currentReviewsPage--;
    this.getReviews();
  }

  getNextNews(): void {
    this.currentNewsPage++;
    this.getNews();
  }

  getPrevNews(): void {
    this.currentNewsPage--;
    this.getNews();
  }

  handleOverlayClick(event: any): void {
    let isMarker = event.overlay.getTitle != undefined;

    if (isMarker) {
      let title = event.overlay.getTitle();
      this.mapInfoWindow.setContent('' + title + '');
      this.mapInfoWindow.open(event.map, event.overlay);
    }
  }

  confirmSubscribe(event: Event) {
    this.confirmationService.confirm({
        target: event.target,
        message: 'Are you sure that you want to subscribe to this offer?',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            this.subscribe();
        },
        reject: () => {
        }
    });
  }

  confirmUnsubscribe(event: Event) {
    this.confirmationService.confirm({
      target: event.target,
      message: 'Are you sure that you want to unsubscribe from this offer?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
          this.unsubscribe();
      },
      reject: () => {
      }
    });
  }

  subscribe(): void {
    this.culturalOffersService.subscribeToCulturalOffer(this.culturalOfferId).subscribe(() => {this.subscribed = true;});
  }

  unsubscribe(): void {
    this.culturalOffersService.unsubscribeFromCulturalOffer(this.culturalOfferId).subscribe(() => {this.subscribed = false;});
  }

  getSubscribed(): void {
    this.culturalOffersService.getSubscribed(this.culturalOfferId).subscribe((isSubscribed) => { this.subscribed = isSubscribed });
  }
}
