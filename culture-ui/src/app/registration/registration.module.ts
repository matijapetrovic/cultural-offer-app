import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegistrationComponent } from './components/registration.component';
import {RegistrationRoutingModule} from '../registration/registration-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [ RegistrationComponent ],
  imports: [
    CommonModule,
    SharedModule,
    RegistrationRoutingModule,
    ReactiveFormsModule,
  ]
})
export class RegistrationModule { }
