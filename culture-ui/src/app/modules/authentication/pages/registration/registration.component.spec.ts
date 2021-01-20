

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AuthenticationService } from '../../authentication.service';

import { RegistrationComponent } from './registration.component';

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistrationComponent ],
      imports: [
        FormsModule,
        HttpClientTestingModule,
        ReactiveFormsModule
      ],
      providers: [{ provide: AuthenticationService, useValue: {}}
      ]})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('isEmptyEmail() should return true if email is not empty', () => {
    let username = '';

    component.registerForm.controls['username'].setValue(username);
    let emailEmpty = component.isEmptyEmail();

    expect(emailEmpty).toBeTrue();
  });

  it('isEmptyEmail() should return false if email is not empty', () => {
    let username = 'username@gmail.com';

    component.registerForm.controls['username'].setValue(username);
    let emailEmpty = component.isEmptyEmail();

    expect(emailEmpty).toBeFalse();
  });

  it('emailIsValid() should return true if email is valid', () => {
    let username = 'username@gmail.com';

    let emailValid = component.emailIsValid(username);

    expect(emailValid).toBeTrue();
  })

  it('emailIsValid() should return false if email is invalid', () => {
    let username = 'username';

    let emailValid = component.emailIsValid(username);

    expect(emailValid).toBeFalse();
  })

  it('isInvalidEmailFormat() should return true if email format is valid and not empty', () => {
    let username = 'username@gmail.com';

    component.registerForm.controls['username'].setValue(username);
    let validEmailFormat = component.isInvalidEmailFormat();

    expect(validEmailFormat).toBeFalse();
  })

  it('isInvalidEmailFormat() should return false if email format is invalid or empty', () => {
    let username = 'username';

    component.registerForm.controls['username'].setValue(username);
    let validEmailFormat = component.isInvalidEmailFormat();

    expect(validEmailFormat).toBeTrue();
  })


  it('isInvalidEmailForm() should return true if email is invalid or empty', () => {
    let username = 'username';

    component.registerForm.controls['username'].setValue(username);
    let validEmailFormat = component.isInvalidEmailForm();

    expect(validEmailFormat).toBeTrue();
  })

  it('isInvalidEmailForm() should return false if email is valid', () => {
    let username = 'username@gmail.com';

    component.registerForm.controls['username'].setValue(username);
    let validEmailFormat = component.isInvalidEmailForm();

    expect(validEmailFormat).toBeFalse();
  })

  it('isFormValid() should return true id email and password are valid', () => {
    let username = 'username@gmail.com';
    let password = 'password';
    let confirmPassword = 'password';

    component.registerForm.controls['username'].setValue(username);
    component.registerForm.controls['password'].setValue(password);
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    let formValid = component.isFormValid();

    expect(formValid).toBeTrue();
  })

  it('isFormValid() should return true id email and password are valid', () => {
    let username = 'username';
    let password = 'password';
    let confirmPassword = 'password';

    component.registerForm.controls['username'].setValue(username);
    component.registerForm.controls['password'].setValue(password);
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    let formValid = component.isFormValid();

    expect(formValid).toBeFalse();
  })

  it('arePasswordsSame() should return true if passwords are same', () => {
    let password = 'password';
    let confirmPassword = 'password';

    component.registerForm.controls['password'].setValue(password);
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    let passwordsAreSame = component.arePasswordsSame();

    expect(passwordsAreSame).toBeTrue();
  });

  it('arePasswordsSame() should return true if passwords are same', () => {
    let password = 'password1';
    let confirmPassword = 'password';

    component.registerForm.controls['password'].setValue(password);
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    let passwordsAreSame = component.arePasswordsSame();

    expect(passwordsAreSame).toBeFalse();
  });

  it('arePasswordsEmpty() should return true if password or confirm password is empty', () => {
    let password = '';
    let confirmPassword = 'password';
  
    component.registerForm.controls['password'].setValue(password);
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    let passwordsAreEmpty = component.arePasswordsEmpty();

    expect(passwordsAreEmpty).toBeTrue();
  });

  it('arePasswordsEmpty() should return false if password and confirm password are not empty', () => {
    let password = 'password';
    let confirmPassword = 'password';

    component.registerForm.controls['password'].setValue(password);
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    let passwordsAreEmpty = component.arePasswordsEmpty();

    expect(passwordsAreEmpty).toBeFalse();
  });

  it('isEmptyPassword() should return false if password is not empty', () => {
    let password = 'password';

    component.registerForm.controls['password'].setValue(password);
    let passwordEmpty = component.isEmptyPassword();

    expect(passwordEmpty).toBeFalse();
  });

  it('isEmptyPassword() should return true if password is empty', () => {
    let password = '';

    component.registerForm.controls['password'].setValue(password);
    let passwordEmpty = component.isEmptyPassword();

    expect(passwordEmpty).toBeTrue();
  });

  it(`passwordMessage() should return 'Passwords must match!' if passwords are not matching`, () => {
    let password = 'password1';
    let confirmPassword = 'password';
    let errorMessage = 'Passwords must match!';

    spyOn(component.registerForm, 'reset')
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    component.registerForm.controls['password'].setValue(password);
    let message = component.passwordMessage();

    expect(message).toEqual(errorMessage);
  });

  it(`passwordMessage() should return 'Password is required!' if passwords are empty`, () => {
    let password = '';
    let confirmPassword = 'password';
    let errorMessage = 'Password is required!';

    spyOn(component.registerForm, 'reset')
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    component.registerForm.controls['password'].setValue(password);
    let message = component.passwordMessage();

    expect(message).toEqual(errorMessage);
  });

  it(`confirmPasswordMessage() should return 'Passwords must match!' if passwords are not matching`, () => {
    let password = 'password1';
    let confirmPassword = 'password';
    let errorMessage = 'Passwords must match!';

    spyOn(component.registerForm, 'reset')
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    component.registerForm.controls['password'].setValue(password);
    let message = component.confirmPasswordMessage();

    expect(message).toEqual(errorMessage);
  });

  it(`confirmPasswordMessage() should return 'Password is required!' if passwords are empty`, () => {
    let password = '';
    let confirmPassword = 'password';
    let errorMessage = 'Password is required!';

    spyOn(component.registerForm, 'reset')
    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
    component.registerForm.controls['password'].setValue(password);
    let message = component.confirmPasswordMessage();

    expect(message).toEqual(errorMessage);
  });

  it('arePasswordErrorsActivated() should return true if  password input is empty', () => {
    let password = '';

    component.registerForm.controls['password'].setValue(password);

    expect(component.arePasswordErrorsActivated()).toBeTrue();
  });

  it('arePasswordErrorsActivated() should return false if  password input is valid', () => {
    let password = 'password';

    component.registerForm.controls['password'].setValue(password);

    expect(component.arePasswordErrorsActivated()).toBeFalse();
  });

  it('areConfirmPasswordErrorsActivated() should return true if confirm password input is empty', () => {
    let confirmPassword = '';

    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);
  
    expect(component.areConfirmPasswordErrorsActivated()).toBeTrue();
  });

  it('areConfirmPasswordErrorsActivated() should return false if confirm password input is valid', () => {
    let confirmPassword = 'password';

    component.registerForm.controls['confirmPassword'].setValue(confirmPassword);

    expect(component.areConfirmPasswordErrorsActivated()).toBeFalse();
  });

  it('removeFormInputs() should reset form inputs', () => {
    let username = 'username@gmail.com';
    let password = 'password';

    spyOn(component.registerForm, 'reset')
    component.registerForm.controls['username'].setValue(username);
    component.registerForm.controls['password'].setValue(password);
    component.removeFormInputs();

    expect(component.registerForm.reset).toHaveBeenCalled()
  })
});
