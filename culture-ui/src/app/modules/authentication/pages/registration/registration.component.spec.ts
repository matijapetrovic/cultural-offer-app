

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AuthenticationService } from '../../authentication.service';
import { fakeAsync } from '@angular/core/testing';

import { RegistrationComponent } from './registration.component';
import { of } from 'rxjs';

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;

  beforeEach(async () => {
    const authenticationServiceMock = {
      register: jasmine.createSpy('register').and.returnValue(of({}))
    };

    await TestBed.configureTestingModule({
      declarations: [ RegistrationComponent ],
      imports: [
        FormsModule,
        HttpClientTestingModule,
        ReactiveFormsModule
      ],
      providers: [{ provide: AuthenticationService, useValue: authenticationServiceMock }
      ]})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));

  it('onSubmit() should send request for registration if form inputs are valid', fakeAsync(() => {
    const firstName = 'firstName';
    const lastName = 'lastName';
    const username = 'username@gmail.com';
    const password = 'password';
    const confirmPassword = 'password';

    spyOn(component.registerForm, 'reset');
    component.loading = true;
    component.submitted = true;
    component.registerForm.controls.firstName.setValue(firstName);
    component.registerForm.controls.lastName.setValue(lastName);
    component.registerForm.controls.username.setValue(username);
    component.registerForm.controls.password.setValue(password);
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    component.onSubmit();

    expect(component.registerForm.pristine).toBeTrue();
  }));

  it('onSubmit() should not send request for registration if form inputs are invalid', fakeAsync(() => {
    const username = 'username@gmail.com';
    const password = 'password';
    const confirmPassword = 'password';

    spyOn(component.registerForm, 'reset');
    component.loading = true;
    component.submitted = true;
    component.registerForm.controls.username.setValue(username);
    component.registerForm.controls.password.setValue(password);
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    component.onSubmit();

    expect(component.registerForm.pristine).toBeTrue();
  }));

  it(`usernameMessage() should return 'Wrong email format!' if email format is not valid`, fakeAsync(() => {
    const username = 'username';
    const message = 'Wrong email format!';

    component.registerForm.controls.username.setValue(username);
    const actualMessage = component.usernameMessage();

    expect(actualMessage).toEqual(message);
  }));

  it(`usernameMessage() should return 'Email is required!' if email is empty`, fakeAsync(() => {
    const username = '';
    const message = 'Email is required!';

    component.submitted = true;
    component.registerForm.controls.username.setValue(username);
    const actualMessage = component.usernameMessage();

    expect(actualMessage).toEqual(message);
  }));

  it('isEmptyEmail() should return true if email is not empty', fakeAsync(() => {
    const username = '';

    component.registerForm.controls.username.setValue(username);
    const emailEmpty = component.isEmptyEmail();

    expect(emailEmpty).toBeTrue();
  }));

  it('isEmptyEmail() should return false if email is not empty', fakeAsync(() => {
    const username = 'username@gmail.com';

    component.registerForm.controls.username.setValue(username);
    const emailEmpty = component.isEmptyEmail();

    expect(emailEmpty).toBeFalse();
  }));

  it('emailIsValid() should return true if email is valid', fakeAsync(() => {
    const username = 'username@gmail.com';

    const emailValid = component.emailIsValid(username);

    expect(emailValid).toBeTrue();
  }));

  it('emailIsValid() should return false if email is invalid', fakeAsync(() => {
    const username = 'username';

    const emailValid = component.emailIsValid(username);

    expect(emailValid).toBeFalse();
  }));

  it('isInvalidEmailFormat() should return true if email format is valid and not empty', fakeAsync(() => {
    const username = 'username@gmail.com';

    component.registerForm.controls.username.setValue(username);
    const validEmailFormat = component.isInvalidEmailFormat();

    expect(validEmailFormat).toBeFalse();
  }));

  it('isInvalidEmailFormat() should return false if email format is invalid or empty', fakeAsync(() => {
    const username = 'username';

    component.registerForm.controls.username.setValue(username);
    const validEmailFormat = component.isInvalidEmailFormat();

    expect(validEmailFormat).toBeTrue();
  }));


  it('isInvalidEmailForm() should return true if email is invalid or empty', fakeAsync(() => {
    const username = 'username';

    component.registerForm.controls.username.setValue(username);
    const validEmailFormat = component.isInvalidEmailForm();

    expect(validEmailFormat).toBeTrue();
  }));

  it('isInvalidEmailForm() should return false if email is valid', fakeAsync(() => {
    const username = 'username@gmail.com';

    component.registerForm.controls.username.setValue(username);
    const validEmailFormat = component.isInvalidEmailForm();

    expect(validEmailFormat).toBeFalse();
  }));

  it('arePasswordsSame() should return true if passwords are same', fakeAsync(() => {
    const password = 'password';
    const confirmPassword = 'password';

    component.registerForm.controls.password.setValue(password);
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    const passwordsAreSame = component.arePasswordsSame();

    expect(passwordsAreSame).toBeTrue();
  }));

  it('arePasswordsSame() should return true if passwords are same', fakeAsync(() => {
    const password = 'password1';
    const confirmPassword = 'password';

    component.registerForm.controls.password.setValue(password);
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    const passwordsAreSame = component.arePasswordsSame();

    expect(passwordsAreSame).toBeFalse();
  }));

  it('arePasswordsEmpty() should return true if password or confirm password is empty', fakeAsync(() => {
    const password = '';
    const confirmPassword = 'password';

    component.registerForm.controls.password.setValue(password);
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    const passwordsAreEmpty = component.arePasswordsEmpty();

    expect(passwordsAreEmpty).toBeTrue();
  }));

  it('arePasswordsEmpty() should return false if password and confirm password are not empty', fakeAsync(() => {
    const password = 'password';
    const confirmPassword = 'password';

    component.registerForm.controls.password.setValue(password);
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    const passwordsAreEmpty = component.arePasswordsEmpty();

    expect(passwordsAreEmpty).toBeFalse();
  }));

  it('isEmptyPassword() should return false if password is not empty', fakeAsync(() => {
    const password = 'password';

    component.registerForm.controls.password.setValue(password);
    const passwordEmpty = component.isEmptyPassword();

    expect(passwordEmpty).toBeFalse();
  }));

  it('isEmptyPassword() should return true if password is empty', fakeAsync(() => {
    const password = '';

    component.registerForm.controls.password.setValue(password);
    const passwordEmpty = component.isEmptyPassword();

    expect(passwordEmpty).toBeTrue();
  }));

  it(`passwordMessage() should return 'Passwords must match!' if passwords are not matching`, fakeAsync(() => {
    const password = 'password1';
    const confirmPassword = 'password';
    const errorMessage = 'Passwords must match!';

    spyOn(component.registerForm, 'reset');
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    component.registerForm.controls.password.setValue(password);
    const message = component.passwordMessage();

    expect(message).toEqual(errorMessage);
  }));

  it('isEmptyConfirmPassword() should return true if confirm password is empty', fakeAsync(() => {
    const confirmPassword = '';

    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    const isEmptyConfirmPassword = component.isEmptyConfirmPassword();

    expect(isEmptyConfirmPassword).toBeTrue();
  }));

  it('isEmptyConfirmPassword() should return true if confirm password is empty', fakeAsync(() => {
    const confirmPassword = 'password';

    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    const isEmptyConfirmPassword = component.isEmptyConfirmPassword();

    expect(isEmptyConfirmPassword).toBeFalse();
  }));

  it(`passwordMessage() should return 'Password is required!' if passwords are empty`, fakeAsync(() => {
    const password = '';
    const confirmPassword = 'password';
    const errorMessage = 'Password is required!';

    spyOn(component.registerForm, 'reset');
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    component.registerForm.controls.password.setValue(password);
    const message = component.passwordMessage();

    expect(message).toEqual(errorMessage);
  }));

  it(`confirmPasswordMessage() should return 'Passwords must match!' if passwords are not matching`, fakeAsync(() => {
    const password = 'password1';
    const confirmPassword = 'password';
    const errorMessage = 'Passwords must match!';

    spyOn(component.registerForm, 'reset');
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    component.registerForm.controls.password.setValue(password);
    const message = component.confirmPasswordMessage();

    expect(message).toEqual(errorMessage);
  }));

  it(`confirmPasswordMessage() should return 'Password is required!' if passwords are empty`, fakeAsync(() => {
    const password = '';
    const confirmPassword = '';
    const errorMessage = 'Password is required!';

    spyOn(component.registerForm, 'reset');
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    component.registerForm.controls.password.setValue(password);
    const message = component.confirmPasswordMessage();

    expect(message).toEqual(errorMessage);
  }));

  it('arePasswordErrorsActivated() should return true if  password input is empty', fakeAsync(() => {
    const password = '';

    component.registerForm.controls.password.setValue(password);

    expect(component.arePasswordErrorsActivated()).toBeTrue();
  }));

  it('arePasswordErrorsActivated() should return false if  password input is valid', fakeAsync(() => {
    const password = 'password';

    spyOn(component, 'areConfirmPasswordErrorsActivated').and.callFake(() => false);
    component.registerForm.controls.password.setValue(password);
    const areActivated = component.areConfirmPasswordErrorsActivated();

    expect(areActivated).toBeFalse();
  }));

  it('areConfirmPasswordErrorsActivated() should return true if confirm password input is empty', fakeAsync(() => {
    const confirmPassword = '';

    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    const areActivated = component.areConfirmPasswordErrorsActivated();

    expect(areActivated).toBeTrue();
  }));

  it('areConfirmPasswordErrorsActivated() should return false if confirm password input is valid', fakeAsync(() => {
    const confirmPassword = 'password';

    spyOn(component, 'areConfirmPasswordErrorsActivated').and.callFake(() => false);
    component.registerForm.controls.confirmPassword.setValue(confirmPassword);
    const areActivated = component.areConfirmPasswordErrorsActivated();

    expect(areActivated).toBeFalse();
  }));

  it('removeFormInputs() should reset form inputs', fakeAsync(() => {
    const username = 'username@gmail.com';
    const password = 'password';

    spyOn(component.registerForm, 'reset');
    component.registerForm.controls.username.setValue(username);
    component.registerForm.controls.password.setValue(password);
    component.removeFormInputs();

    expect(component.registerForm.reset).toHaveBeenCalled();
  }));
});
