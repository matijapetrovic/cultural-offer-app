import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../../authentication.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
     public messageService: MessageService
    ) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern('^[^\s@]+@[^\s@]+\.[^\s@]+$')]],
      password: ['', Validators.required, ],
      confirmPassword: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
    });
  }

  get f(): any { return this.registerForm.controls; }

  onSubmit(): void {
    this.submitted = true;

    if (this.isInvalidEmailForm() || !this.arePasswordsSame()) {
      this.messageService.add({
        severity: 'info', summary: 'Registration info', detail: 'Registration data is not valid!'
      });
      setTimeout(() => this.messageService.clear(), 2000);
      return;
    }

    this.loading = true;
    this.authenticationService.register(
      {
        firstName: this.f.firstName.value,
        lastName: this.f.lastName.value,
        email: this.f.username.value,
        password: this.f.password.value
      })
      .subscribe();
    this.messageService.add({
      severity: 'success', summary: 'Registration successful', detail: 'You have successfully registered user!'
    });
    setTimeout(() => this.messageService.clear(), 2000);
    this.registerForm.reset();
  }

  usernameMessage(): string {
    if (this.isInvalidEmailFormat()) {
      return 'Wrong email format!';
    } else if (this.isEmptyEmail() && this.submitted) {
      return 'Email is required!';
    }
  }

  isEmptyEmail(): boolean {
    if (this.f.username.value === '' || this.f.username.value === null) {
      return true;
    }
    return false;
  }

  emailIsValid(email): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }

  isInvalidEmailFormat(): boolean {
    if (!this.emailIsValid(this.f.username.value) && !this.isEmptyEmail()) {
      return true;
    }
    return false;
  }

  isInvalidEmailForm(): boolean {
    if (this.isInvalidEmailFormat() || this.isEmptyEmail()) {
      return true;
    }
    return false;
  }

  arePasswordsSame(): boolean {
    return (this.f.password.value === this.f.confirmPassword.value);
  }

  arePasswordsEmpty(): boolean {
    if (this.isEmptyPassword() || this.isEmptyConfirmPassword()) {
      return true;
    }
    return false;
  }

  isEmptyPassword(): boolean {
    if (this.f.password.value === '' || this.f.password.value === null) {
      return true;
    }
    return false;
  }

  isEmptyConfirmPassword(): boolean {
    if (this.f.confirmPassword.value === '' || this.f.confirmPassword.value === null) {
      return true;
    }
    return false;
  }

  passwordMessage(): string {
    if (!this.arePasswordsSame() && !this.arePasswordsEmpty()) {
      return 'Passwords must match!';
    }
    else if ((this.f.password.value === '' || this.f.password.value === null) && this.arePasswordErrorsActivated()) {
      return 'Password is required!';
    }
  }

  confirmPasswordMessage(): string {
    if (!this.arePasswordsSame() && !this.arePasswordsEmpty()) {
      return 'Passwords must match!';
    }
    else if ((this.f.confirmPassword.value === '' || this.f.confirmPassword.value === null) && this.areConfirmPasswordErrorsActivated()) {
      return 'Password is required!';
    }
  }

  arePasswordErrorsActivated(): boolean {
    return this.f.password.errors.required || this.f.password.errors;
  }

  areConfirmPasswordErrorsActivated(): boolean {
    return this.f.confirmPassword.errors.required || this.f.confirmPassword.errors;
  }

  removeFormInputs(): void {
    this.registerForm.reset();
  }
}
