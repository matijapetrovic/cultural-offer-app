import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Router, ActivatedRoute } from '@angular/router';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  const route = {
      snapshot: {
        queryParams: {
          returnUrl: null
        }
      }
    };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [
        FormsModule,
        HttpClientTestingModule,
        ReactiveFormsModule
      ],
      providers: [{ provide: ActivatedRoute, useValue: route },
                  { provide: Router, useValue: {} }]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it(`usernameMessage() should return 'Wrong email format!' when email format is invalid`, () => {
    const username = 'username';
    const usernameMessage = 'Wrong email format!';

    component.loginForm.controls.username.setValue(username);
    const message = component.usernameMessage();

    expect(message).toEqual(usernameMessage);
  });

  it(`usernameMessage() should return 'Email is required!!' when email format is empty`, () => {
    const username = '';
    const usernameMessage = 'Email is required!';

    component.loginForm.controls.username.setValue(username);
    const message = component.usernameMessage();

    expect(message).toEqual(usernameMessage);
  });

  it('onSubmit() should submit if form is valid', () => {
    const username = 'username@gmail.com';
    const password = 'password';

    component.loginForm.controls.username.setValue(username);
    component.loginForm.controls.password.setValue(password);
    component.onSubmit();

    expect(component.loginForm.pristine).toBeTrue();
  });

  it('onSubmit() should not submit if form is invalid', () => {
    const username = 'username';
    const password = 'password';

    spyOn(component, 'isInvalidEmailForm');
    component.loginForm.controls.username.setValue(username);
    component.loginForm.controls.password.setValue(password);
    component.onSubmit();

    expect(component.loginForm.pristine).toBeTrue();
    expect(component.isInvalidEmailForm).toHaveBeenCalled();
  });

  it('isEmptyEmail() should return false if email is not empty', () => {
    const username = 'username@gmail.com';

    component.loginForm.controls.username.setValue(username);

    const isUsernameEmpty = component.isEmptyEmail();

    expect(isUsernameEmpty).toBeFalse();
  });

  it('isEmptyEmail() should return true if email is empty', () => {
    const username = '';

    component.loginForm.controls.username.setValue(username);

    const isUsernameEmpty = component.isEmptyEmail();

    expect(isUsernameEmpty).toBeTrue();
  });

  it('emailIsValid() should return true if email is valid', () => {
    const email = 'username@gmail.com';

    const isEmailValid = component.emailIsValid(email);

    expect(isEmailValid).toBeTrue();
  });

  it('emailIsValid() should return false if email is not valid', () => {
    const email = 'username';

    const isEmailValid = component.emailIsValid(email);

    expect(isEmailValid).toBeFalse();
  });

  it('isInvalidEmailFormat() should return true if email is invalid', () => {
    const email = 'username';

    component.loginForm.controls.username.setValue(email);
    const isEmailValid = component.isInvalidEmailFormat();

    expect(isEmailValid).toBeTrue();
  });

  it('isInvalidEmailFormat() should return false if email is valid', () => {
    const email = 'username@gmail.com';

    component.loginForm.controls.username.setValue(email);
    const isEmailValid = component.isInvalidEmailFormat();

    expect(isEmailValid).toBeFalse();
  });

  it('isInvalidEmailForm() should return true if email form input is empty or invalid', () => {
    let email = 'username';

    component.loginForm.controls.username.setValue(email);
    const isEmailInvalid = component.isInvalidEmailForm();
    email = '';
    component.loginForm.controls.username.setValue(email);
    const isEmailEmpty = component.isInvalidEmailForm();

    expect(isEmailInvalid).toBeTrue();
    expect(isEmailEmpty).toBeTrue();
  });

  it('isInvalidEmailForm() should return false if email form input is not empty or invalid', () => {
    const email = 'username@gmail.com';

    component.loginForm.controls.username.setValue(email);
    const isEmailInvalid = component.isInvalidEmailForm();

    expect(isEmailInvalid).toBeFalse();
  });

  it('isEmptyPassword() should return false when passsword is not empty', () => {
    const password = 'password';

    component.loginForm.controls.password.setValue(password);

    const isPasswordEmpty = component.isEmptyPassword();

    expect(isPasswordEmpty).toBeFalse();
  });

  it('isEmptyPassword() should return true when passsword is empty', () => {
    const password = '';

    component.loginForm.controls.password.setValue(password);

    const isPasswordEmpty = component.isEmptyPassword();

    expect(isPasswordEmpty).toBeTrue();
  });
});
