import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BehaviorSubject } from 'rxjs';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { Role } from 'src/app/modules/authentication/role';
import { User } from 'src/app/modules/authentication/user';
import { mockAdmin, mockUser } from 'src/app/shared/testing/mock-data';

import { HeaderComponent } from './header.component';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let authenticationService: AuthenticationService;

  beforeEach(async () => {
    const userSubject = new BehaviorSubject<User>(null);
    const authenticationServiceMock = {
      logout: jasmine.createSpy('logout')
        .and.callFake(() => userSubject.next(null)),
      currentUserSubject: userSubject,
      login: jasmine.createSpy('login')
        .and.callFake((username: string, password: string) => { 
          if (username == 'admin') userSubject.next(mockAdmin);
          if (username == 'user') userSubject.next(mockUser); 
        }),
      currentUser: userSubject.asObservable()
    };

    await TestBed.configureTestingModule({
      declarations: [ HeaderComponent ],
      providers: [
        { provide: AuthenticationService, useValue: authenticationServiceMock }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    authenticationService = TestBed.inject(AuthenticationService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.authenticated).toBeFalse();
  });

  it('should update header items when regular user logs in', () => {
    authenticationService.login('user', '');

    expect(component.authenticated).toBeTrue();
    component.commonItems.forEach((item) => expect(component.items).toContain(item));
    component.userItems.forEach((item) => expect(component.items).toContain(item));
    component.adminItems.forEach((item) => expect(component.items).not.toContain(item));
  });

  it('should update header items when admin logs in', () => {
    authenticationService.login('admin', '');

    expect(component.authenticated).toBeTrue();
    component.commonItems.forEach((item) => expect(component.items).toContain(item));
    component.userItems.forEach((item) => expect(component.items).not.toContain(item));
    component.adminItems.forEach((item) => expect(component.items).toContain(item));
  });

  it('should update header items when user logs out', () => {
    authenticationService.logout();

    expect(component.authenticated).toBeFalse();
    component.commonItems.forEach((item) => expect(component.items).toContain(item));
    component.userItems.forEach((item) => expect(component.items).not.toContain(item));
    component.adminItems.forEach((item) => expect(component.items).not.toContain(item));
    component.unauthenticatedItems.forEach((item) => expect(component.items).toContain(item));
  });

  it('should logout user when logout button is clicked', () => {
    component.logout();
    expect(authenticationService.logout).toHaveBeenCalled();
  });
});
