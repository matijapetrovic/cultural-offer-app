import { Component, OnInit } from '@angular/core';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { Category } from 'src/app/modules/categories/category';
import { SubcategoriesService } from 'src/app/modules/subcategories/subcategories.service';
import { Subcategory } from 'src/app/modules/subcategories/subcategory';
import { CulturalOfferLocation, CulturalOfferLocationsFilter, LocationRange } from 'src/app/modules/cultural-offers/cultural-offer';
import { CulturalOffersService } from 'src/app/modules/cultural-offers/cultural-offers.service';
import { GeolocationService } from 'src/app/core/services/geolocation.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-offer-map-page',
  templateUrl: './offer-map-page.component.html',
  styleUrls: ['./offer-map-page.component.scss']
})
export class OfferMapPageComponent implements OnInit {

  culturalOffers: CulturalOfferLocation[];
  categories: Category[] = [];
  subcategories: Subcategory[] = [];

  private locationRange: LocationRange;
  private categoryId: number;
  private subcategoryId: number;

  constructor(
    private culturalOffersService: CulturalOffersService,
    private categoriesService: CategoriesService,
    private subcategoriesService: SubcategoriesService,
    private geolocationService: GeolocationService,
    private messageService: MessageService
  ) {
    this.locationRange = {
      latitudeFrom: 44.66841545724345,
      latitudeTo: 44.72171309183708,
      longitudeFrom: 19.47059610752435,
      longitudeTo: 19.5262616937845
    };
    this.categoryId = null;
    this.subcategoryId = null;
  }

  ngOnInit(): void {
    this.getCulturalOfferLocations();
    this.getCategories();
  }

  getCulturalOfferLocations(): void {
    this.culturalOffersService.getCulturalOfferLocations(
      this.locationRange,
      this.categoryId,
      this.subcategoryId
    ).subscribe(culturalOffers => this.culturalOffers = culturalOffers);
  }

  getCategories(): void {
    this.categoriesService.getCategoryNames().subscribe(categories => this.categories = categories);
  }

  getSubcategories(category: Category): void {
    this.subcategoriesService.getSubcategoryNames(category.id).subscribe(subcategories => this.subcategories = subcategories);
  }

  searchLocation(location: string): void {
    this.geolocationService.geocode(location).subscribe(
      (locationRange) => {
        this.locationRange = locationRange;
        this.getCulturalOfferLocations();
      },
      (error) => {
        if (error.message) {
          this.messageService.add({ severity: 'warn', summary: 'Error!', detail: error.message });

        }
      });
  }

  updateFilters(event: CulturalOfferLocationsFilter): void {
    if (event.category) {
      this.categoryId = event.category.id;
    }
    if (event.subcategory) {
      this.subcategoryId = event.subcategory.id;
    }
    this.getCulturalOfferLocations();
  }

  updateLocations(event: LocationRange): void {
    this.locationRange = event;
    this.getCulturalOfferLocations();
  }

  resetFilters(): void {
    this.categoryId = null;
    this.subcategoryId = null;
    this.subcategories = [];
    this.getCulturalOfferLocations();
  }

}
