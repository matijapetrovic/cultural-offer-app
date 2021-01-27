import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { ActivationComponent } from './pages/activation/activation.component';

const routes: Routes = [
  {
    path: 'register',
    component: RegistrationComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'activate/:id',
    component: ActivationComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthenticationRoutingModule { }
