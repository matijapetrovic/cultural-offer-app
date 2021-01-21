import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BehaviorSubject } from 'rxjs';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { User } from 'src/app/modules/authentication/user';
import { RoundPipe } from 'src/app/shared/pipes/round.pipe';
import { mockAdmin, mockReview, mockUser } from 'src/app/shared/testing/mock-data';

import { OfferReviewItemComponent } from './offer-review-item.component';

describe('OfferReviewItemComponent', () => {
  let component: OfferReviewItemComponent;
  let fixture: ComponentFixture<OfferReviewItemComponent>;

  beforeEach(async () => {
    const userSubject = new BehaviorSubject<User>(null);
    const authenticationServiceMock = {
      logout: jasmine.createSpy('logout')
        .and.callFake(() => userSubject.next(null)),
      currentUserSubject: userSubject,
      login: jasmine.createSpy('login')
        .and.callFake((username: string, password: string) => {
          if (username === 'admin') {
            userSubject.next(mockAdmin);
          }
          if (username === 'user') {
            userSubject.next(mockUser);
          }
        }),
      currentUser: userSubject.asObservable()
    };
    await TestBed.configureTestingModule({
      declarations: [ OfferReviewItemComponent, RoundPipe ],
      providers: [
        { provide: AuthenticationService, useValue: authenticationServiceMock }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferReviewItemComponent);
    component = fixture.componentInstance;
    component.review = mockReview;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
