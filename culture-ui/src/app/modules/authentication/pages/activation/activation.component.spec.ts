import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../../authentication.service';
import { of } from 'rxjs';
import { fakeAsync } from '@angular/core/testing';

import { ActivationComponent } from './activation.component';

describe('ActivationComponent', () => {
  let component: ActivationComponent;
  let fixture: ComponentFixture<ActivationComponent>;

  beforeEach(async () => {
    const route = {
      snapshot: {
        params: {
          id: 10
        }
      }
    };

    const authenticationServiceMock = {
      activate: jasmine.createSpy('activate').and.returnValue(of({}))
    };

    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    await TestBed.configureTestingModule({
      declarations: [ ActivationComponent ],
      providers: [{ provide: ActivatedRoute, useValue: route },
      { provide: Router, useValue: routerMock },
      { provide: AuthenticationService, useValue: authenticationServiceMock }]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it(`activate() should navigate to map page`, fakeAsync(() => {
    component.ngOnInit();

    expect(component.router.navigate).toHaveBeenCalled();
  }));
});
