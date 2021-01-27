import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './pages/login/login.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { RegistrationComponent } from './pages/registration/registration.component';
import { AuthenticationRoutingModule } from './authentication-routing.module';
import { ActivationComponent } from './../authentication/pages/activation/activation.component';

@NgModule({
  declarations: [LoginComponent, RegistrationComponent, ActivationComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AuthenticationRoutingModule,
    SharedModule
  ]
})
export class AuthenticationModule { }
