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
      routerLink: [''],
      id: 'home-nav-link'
    },
    {
      label: 'Map',
      icon: 'pi pi-map',
      routerLink: ['/map'],
      id: 'map-nav-link'
    }
  ];

  userItems: MenuItem[] = [
    {
      label: 'Dashboard',
      routerLink: ['/dashboard'],
      id: 'dashboard-nav-link'
    }
  ];

  adminItems: MenuItem[] = [
    {
      label: 'Categories',
      routerLink: ['/categories'],
      id: 'categories-nav-link'
    },
    {
      label: 'Subcategories',
      routerLink: ['/subcategories'],
      id: 'subcategories-nav-link'
    },
    {
      label: 'News',
      routerLink: ['news'],
      id: 'news-nav-link'
    }
  ];

  unauthenticatedItems: MenuItem[] = [
    {
      label: 'Log in',
      routerLink: ['/auth/login'],
      id: 'login-nav-link'
    },
    {
      label: 'Register',
      routerLink: ['/auth/register'],
      id: 'register-nav-link'
    }
  ];

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.authenticationService.currentUser.subscribe(user => this.updateItems(user));
  }

  updateItems(user: User): void {
    if (!!user) {
      this.authenticated = true;
      if (user.role.includes(Role.ROLE_ADMIN)) {
        this.items = [
          ...this.commonItems,
          ...this.adminItems
        ];
      }
      else if (user.role.includes(Role.ROLE_USER)) {
        this.items = [
          ...this.commonItems,
          ...this.userItems
        ];
      }
    }
    else {
      this.authenticated = false;
      this.items = [
        ...this.commonItems,
        ...this.unauthenticatedItems
      ];
    }
  }

  logout(): void {
    this.authenticationService.logout();
  }
}

