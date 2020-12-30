import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { first } from 'rxjs/operators';

import { AuthenticationService } from '../../_services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern("^[^\s@]+@[^\s@]+\.[^\s@]+$")]],
      password: ['', Validators.required],
    });
    
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.isInvalidEmailForm()) {
      return;
    }

    this.loading = true;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.error = error;
          this.loading = false;
        });

    this.removeFormInputs();
  }

  usernameMessage() {
    if (this.isInvalidEmailFormat()){
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

  isEmptyPassword() {
    if (this.f.password.value === "" || this.f.password.value === null) {
      return true;
    }
    return false;
  }

  isFormValid() {
    if (!this.isInvalidEmailForm && !this.isEmptyPassword()){
      return true;
    }
    return false;
  }

  removeFormInputs() {
    this.loginForm.reset();
  }
}
