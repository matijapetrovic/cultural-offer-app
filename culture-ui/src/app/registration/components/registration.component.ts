import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
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
    private route: ActivatedRoute,
    private router: Router,
    private registrationService: RegistrationService
    ) {

     }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern("^[^\s@]+@[^\s@]+\.[^\s@]+$")]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
    });
  }

  get f() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.isInvalidEmailForm()) {
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
    } else if (this.isEmptyEmail()) {
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

  isEmptyPassword() {
    if (this.f.password.value === "" || this.f.password.value === null) {
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
    return this.f.password.value === this.f.confirmPassword.value;
  }

  removeFormInputs() {
    this.registerForm.reset();
  }

}
