import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegistrationService } from '../registration.service';

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
    private registrationService: RegistrationService
    ) {

     }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern("^[^\s@]+@[^\s@]+\.[^\s@]+$")]],
      password: ['', Validators.required, ],
      confirmPassword: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
    });
  }

  get f() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.isInvalidEmailForm() || !this.arePasswordsSame()) {
      return;
    }

    this.loading = true;
    this.registrationService.register(
      {
        firstName: this.f.firstName.value,
        lastName: this.f.lastName.value,
        email: this.f.username.value,
        password: this.f.password.value
      })
      .subscribe();
    this.removeFormInputs();
  }

  usernameMessage() {
    if (this.isInvalidEmailFormat()) {
      return "Wrong email format!";
    } else if (this.isEmptyEmail() && this.submitted) {
      return "Email is required!"
    }
  }

  isEmptyEmail() {
    if (this.f.username.value === "" || this.f.username.value === null) {
      return true;
    }
    return false;
  }

  emailIsValid(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  }

  isInvalidEmailFormat() {
    if (!this.emailIsValid(this.f.username.value) && !this.isEmptyEmail()) {
      return true;
    }
    return false;
  }

  isInvalidEmailForm() {
    if (this.isInvalidEmailFormat() || this.isEmptyEmail()) {
      return true;
    }
    return false;
  }

  isFormValid() {
    if (!this.isInvalidEmailForm && !this.isEmptyPassword()) {
      return true;
    }
    return false;
  }

  arePasswordsSame() {
    return (this.f.password.value === this.f.confirmPassword.value);
  }

  arePasswordsEmpty() {
    if(this.isEmptyPassword() || this.isEmptyConfirmPassword()) {
      return true;
    }
    return false;
  }

  isEmptyPassword() {
    if (this.f.password.value === "" || this.f.password.value === null) {
      return true;
    }
    return false;
  }

  isEmptyConfirmPassword() {
    if (this.f.confirmPassword.value === "" || this.f.confirmPassword.value === null) {
      return true;
    }
    return false;
  }

  passwordMessage() {
    if (!this.arePasswordsSame() && !this.arePasswordsEmpty()) {
      return "Passwords must match!"; 
    } 
    else if ((this.f.password.value === '' || this.f.password.value === null) && this.arePasswordErrorsActivated()) {
      return "Password is required!";
    }
  }

  confirmPasswordMessage() {
    if (!this.arePasswordsSame() && !this.arePasswordsEmpty()) {
      return "Passwords must match!";
    }
    else if ((this.f.confirmPassword.value === '' || this.f.confirmPassword.value === null) && this.areConfirmPasswordErrorsActivated()) {
      return "Password is required!";
    }
  }

  arePasswordErrorsActivated() {
    return this.f.password.errors.required || this.f.password.errors
  }

  areConfirmPasswordErrorsActivated() {
    return this.f.confirmPassword.errors.required || this.f.confirmPassword.errors
  }

  removeFormInputs() {
    this.registerForm.reset();
  }
}
