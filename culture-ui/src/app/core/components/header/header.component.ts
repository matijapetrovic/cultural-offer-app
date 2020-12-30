import { Component, OnInit } from '@angular/core';
import {MenuItem} from 'primeng/api';
import { Role } from 'src/app/_models';
import { AuthenticationService } from 'src/app/_services';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  items: MenuItem[];

  adminItems: MenuItem[] = [
    {
      label: 'Categories',
      routerLink: ['/categories']
    },
    {
      label: 'Subcategories',
      routerLink: ['subcategories']
    },
    {
      label: 'News',
      routerLink: ['news']
    },
  ];

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        routerLink: ['/']
      },
      {
        label: 'Map',
        icon: 'pi pi-map',
        routerLink: ['/cultural-offers']
      }
    ];
    if (this.authenticationService.currentUserValue.role == Role.Admin) {
      this.items = [...this.items, ...this.adminItems];
    }
  }

}
