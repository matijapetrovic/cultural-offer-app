import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { fakeAsync, tick } from '@angular/core/testing';
import { AuthenticationService } from './authentication.service';
import { } from 'jasmine';
import { User } from './user';
import { Role } from './role';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { RegisterRequest } from './register';
import { BehaviorSubject } from 'rxjs';

describe('AuthenticationService', () => {
  let injector;
  let service: AuthenticationService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  const errorHandler = jasmine.createSpyObj('errorHandler', ['handleError']);
  const authenticationUrl = `${environment.apiUrl}/api/auth`;

  beforeEach(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate').and.callFake((path: string) => {})
    };

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ AuthenticationService,
                  { provide: Router, useValue: routerMock } ]
    });

    injector = getTestBed();
    service = TestBed.inject(AuthenticationService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('currentUserValue() should return current logged user if exists', fakeAsync(() => {
    const mockUserSubject: User = {
      id: 1,
      username: 'username@gmail.com',
      password: 'password',
      firstName: 'A',
      lastName: 'A',
      role: Role.User
    };
    const currentUserSubjectMock: BehaviorSubject<User> = new BehaviorSubject<User>(mockUserSubject);
    spyOn<any>(service, 'currentUserValue').and.returnValue(mockUserSubject);
    service.currentUserSubject = currentUserSubjectMock;
    const user = service.currentUserValue;
    tick();

    expect(service).toBeTruthy();
    expect(user).toBeTruthy();
    expect(user.id).toEqual(mockUserSubject.id);
    expect(user.password).toEqual(mockUserSubject.password);
    expect(user.firstName).toEqual(mockUserSubject.firstName);
    expect(user.lastName).toEqual(mockUserSubject.lastName);
    expect(user.firstName).toEqual(mockUserSubject.firstName);
    expect(user.role).toEqual(mockUserSubject.role);
  }));

  it('currentUserValue() should return null if logged user does not exist', fakeAsync(() => {
    const mockUserSubject: User = null;
    spyOn<any>(service, 'currentUserValue').and.returnValue(mockUserSubject);
    const user = service.currentUserValue;
    tick();

    expect(service).toBeTruthy();
    expect(user).toBeNull();
  }));

  it('isAuthenticated() should return true if user is logged', fakeAsync(() => {
    const mockUserSubject: User = {
      id: 1,
      username: 'username@gmail.com',
      password: 'password',
      firstName: 'A',
      lastName: 'A',
      role: Role.User
    };

    spyOn<any>(service, 'currentUserValue').and.returnValue(mockUserSubject);
    spyOn<any>(service, 'isAuthenticated').and.returnValue(true);

    expect(service).toBeTruthy();
    expect(service.isAuthenticated()).toBeTrue();
  }));

  it('isAuthenticated() should return false if there is no logged user', fakeAsync(() => {
    const mockUserSubject: User = null;
    spyOn<any>(service, 'currentUserValue').and.returnValue(mockUserSubject);
    spyOn<any>(service, 'isAuthenticated').and.returnValue(false);

    expect(service).toBeTruthy();
    expect(service.isAuthenticated()).toBeFalse();
  }));

  it('login() should log in user', fakeAsync(() => {
    let response: any;
    const credentials = {
      email: 'user@gmail.com',
      password: 'password'
    };

    const mockUser = {
      id: 1,
      username: 'username@gmail.com',
      password: 'password',
      firstName: 'A',
      lastName: 'A',
      role: Role.User
    };

    spyOn(service, 'login').and.callThrough();
    service.login(credentials.email, credentials.password).subscribe(data => { response = data; });

    const req = httpMock.expectOne(authenticationUrl + '/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);

    tick();

    expect(response).toBeTruthy();
  }));

  it('logout() should remove current logged user from local storage', fakeAsync(() => {
    service.logout();
    tick();

    expect(localStorage.getItem('currentUser')).toBeNull();
  }));

  it('register() should save user', fakeAsync(() => {
    const registerRequest: RegisterRequest = {
      firstName: 'A',
      lastName: 'A',
      email: 'username@gmail.com',
      password: 'password'
    };

    spyOn(service, 'register').and.callThrough();
    service.register(registerRequest).subscribe();

    const req = httpMock.expectOne(authenticationUrl + '/register');
    expect(req.request.method).toBe('POST');
    req.flush(registerRequest);
    tick();
  }));
});
