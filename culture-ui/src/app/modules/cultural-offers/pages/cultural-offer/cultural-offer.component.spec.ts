import { DebugElement } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { BehaviorSubject, of } from 'rxjs';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { User } from 'src/app/modules/authentication/user';
import { NewsService } from 'src/app/modules/news/news.service';
import { ReviewsService } from 'src/app/modules/reviews/reviews.service';
import { RoundPipe } from 'src/app/shared/pipes/round.pipe';
import { mockAdmin, mockCulturalOffer, mockNewsPage, mockReviewPage, mockUser } from 'src/app/shared/testing/mock-data';
import { ActivatedRouteStub } from 'src/app/shared/testing/router-stubs';
import { CulturalOffersService } from '../../cultural-offers.service';

import { CulturalOfferComponent } from './cultural-offer.component';

describe('CulturalOfferComponent', () => {
  let component: CulturalOfferComponent;
  let fixture: ComponentFixture<CulturalOfferComponent>;

  let culturalOffersService: CulturalOffersService;
  let reviewsService: ReviewsService;
  let newsService: NewsService;
  let authenticationService: AuthenticationService;

  beforeEach(async () => {

    const culturalOffersServiceMock = {
      getCulturalOffer: jasmine.createSpy('getCulturalOffer')
        .and.returnValue(of(mockCulturalOffer)),
      subscribeToCulturalOffer: jasmine.createSpy('subscribeToCulturalOffer')
        .and.returnValue(of({})),
      unsubscribeFromCulturalOffer: jasmine.createSpy('unsubscribeFromCulturalOffer')
        .and.returnValue(of({})),
      getSubscribed: jasmine.createSpy('getSubscribed')
        .and.returnValue(of(true))
    };

    const reviewsServiceMock = {
      getReviews: jasmine.createSpy('getReviews')
        .and.returnValue(of(mockReviewPage))
    };

    const newsServiceMock = {
      getNews: jasmine.createSpy('getNews')
        .and.returnValue(of(mockNewsPage))
    };

    const userSubject = new BehaviorSubject<User>(null);
    const authenticationServiceMock = {
      logout: jasmine.createSpy('logout')
        .and.callFake(() => userSubject.next(null)),
      currentUserSubject: userSubject,
      login: jasmine.createSpy('login')
        .and.callFake((username: string, password: string) => {
          if (username == 'admin') { userSubject.next(mockAdmin); }
          if (username == 'user') { userSubject.next(mockUser); }
        }),
      currentUser: userSubject.asObservable()
    };

    const confirmationServiceMock = {
      confirm: jasmine.createSpy('confirm')
    };

    const activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = {id: 1};

    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferComponent, RoundPipe ],
      providers: [
        { provide: CulturalOffersService, useValue: culturalOffersServiceMock },
        { provide: ReviewsService, useValue: reviewsServiceMock },
        { provide: NewsService, useValue: newsServiceMock },
        { provide: AuthenticationService, useValue: authenticationServiceMock },
        { provide: ConfirmationService, useValue: confirmationServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteStub }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferComponent);
    component = fixture.componentInstance;
    culturalOffersService = TestBed.inject(CulturalOffersService);
    reviewsService = TestBed.inject(ReviewsService);
    newsService = TestBed.inject(NewsService);
    authenticationService = TestBed.inject(AuthenticationService);

    fixture.detectChanges();
  });

  it('should create and init', fakeAsync(() => {
    expect(component).toBeTruthy();

    tick();

    expect(component.culturalOffer).toEqual(mockCulturalOffer);
    expect(component.reviewPage).toEqual(mockReviewPage);
    expect(component.newsPage).toEqual(mockNewsPage);
    expect(component.mapInfoWindow).toBeTruthy();

    expect(component.mapOptions).toEqual({
      center: { lat: mockCulturalOffer.latitude, lng: mockCulturalOffer.longitude },
      zoom: 9
    });

    expect(component.mapOverlays.length).toEqual(1);

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const reviewItems: DebugElement[] = fixture.debugElement.queryAll(By.css('ul.review-items > li'));
    expect(reviewItems.length).toBe(mockReviewPage.data.length);

    const newsItems: DebugElement[] = fixture.debugElement.queryAll(By.css('ul.news-items > li'));
    expect(newsItems.length).toBe(mockNewsPage.data.length);
  }));

  it('should update subscribed regular user logs in', fakeAsync(() => {
    authenticationService.login('user', '');
    tick();

    expect(component.subscribed).toBeTrue();
    expect(culturalOffersService.getSubscribed).toHaveBeenCalled;
  }));

  it('should update subscribed admin logs in', () => {
    authenticationService.login('admin', '');

    expect(component.subscribed).toBe(null);
  });

  it('should update subscribed user logs out', () => {
    authenticationService.logout();

    expect(component.subscribed).toBe(null);
 });

  it('should get next news when next button is clicked', fakeAsync(() => {
    component.newsPage = null;
    component.getNextNews();
    tick();

    expect(newsService.getNews).toHaveBeenCalledWith(1, 1, 3);
    expect(component.newsPage).toEqual(mockNewsPage);
  }));

  it('should get next reviews when next button is clicked', fakeAsync(() => {
    component.reviewPage = null;
    component.getNextReviews();
    tick();

    expect(reviewsService.getReviews).toHaveBeenCalledWith(1, 1, 3);
    expect(component.reviewPage).toEqual(mockReviewPage);
  }));

  it('should get previous news when previous button is clicked', fakeAsync(() => {
    component.getNextNews();
    tick();
    component.newsPage = null;
    component.getPrevNews();
    tick();

    expect(newsService.getNews).toHaveBeenCalledWith(1, 0, 3);
    expect(component.newsPage).toEqual(mockNewsPage);
  }));

  it('should get previous reviews when previous button is clicked', fakeAsync(() => {
    component.getNextReviews();
    tick();
    component.reviewPage = null;
    component.getPrevReviews();
    tick();

    expect(reviewsService.getReviews).toHaveBeenCalledWith(1, 0, 3);
    expect(component.reviewPage).toEqual(mockReviewPage);
  }));

  it('should subscribe when subscribe button is clicked', fakeAsync(() => {
    component.subscribe();
    tick();
    tick(2000);

    expect(culturalOffersService.subscribeToCulturalOffer).toHaveBeenCalledWith(1);
    expect(component.subscribed).toBeTrue();
  }));

  it('should unsubscribe when unsubscribe button is clicked', fakeAsync(() => {
    component.unsubscribe();
    tick();
    tick(2000);

    expect(culturalOffersService.unsubscribeFromCulturalOffer).toHaveBeenCalledWith(1);
    expect(component.subscribed).toBeFalse();
  }));
});
