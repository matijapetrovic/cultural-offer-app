import { Component, OnInit } from '@angular/core';
import {MenuItem} from 'primeng/api';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { Role } from 'src/app/modules/authentication/role';
import { User } from 'src/app/modules/authentication/user';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  items: MenuItem[];

  authenticated: boolean;

  commonItems: MenuItem[] = [
    {
      label: 'Home',
      icon: 'pi pi-home',
      routerLink: ['']
    },
    {
      label: 'Map',
      icon: 'pi pi-map',
      routerLink: ['/map']
    }
  ];

  userItems: MenuItem[] = [
    {
      label: 'Dashboard',
      routerLink: ['/dashboard']
    }
  ];

  adminItems: MenuItem[] = [
    {
      label: 'Categories',
      routerLink: ['/categories']
    },
    {
      label: 'Subcategories',
      routerLink: ['/subcategories']
    },
    {
      label: 'News',
      routerLink: ['news']
    }
  ];

  unauthenticatedItems: MenuItem[] = [
    {
      label: 'Log in',
      routerLink: ['/auth/login']
    },
    {
      label: 'Register',
      routerLink: ['/auth/register']
    }
  ];

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.authenticationService.currentUser.subscribe(user => this.updateItems(user));
  }

  updateItems(user: User): void {
    if (!!user) {
      this.authenticated = true;
      if (user.role == Role.Admin) {
        this.items = [
          ...this.commonItems,
          ...this.adminItems
        ]
      }
      else if (user.role == Role.User) {
        this.items = [
          ...this.commonItems,
          ...this.userItems
        ]
      }
    }
    else {
      this.authenticated = false;
      this.items = [
        ...this.commonItems,
        ...this.unauthenticatedItems
      ]
    }
  }

  logout(): void {
    this.authenticationService.logout();
  }
}

