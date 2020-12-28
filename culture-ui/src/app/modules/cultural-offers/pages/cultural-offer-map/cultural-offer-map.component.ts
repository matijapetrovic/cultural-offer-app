import { Component, OnInit } from '@angular/core';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { Category } from 'src/app/modules/categories/category';
import { SubcategoriesService } from 'src/app/modules/subcategories/subcategories.service';
import { Subcategory } from 'src/app/modules/subcategories/subcategory';
import { CulturalOfferLocation, CulturalOfferLocationsFilter, LocationRange } from '../../cultural-offer';
import { CulturalOffersService } from '../../cultural-offers.service';

@Component({
  selector: 'app-cultural-offer-map',
  templateUrl: './cultural-offer-map.component.html',
  styleUrls: ['./cultural-offer-map.component.scss']
})
export class CulturalOfferMapComponent implements OnInit {
  culturalOffers: CulturalOfferLocation[];
  categories: Category[] = [];
  subcategories: Subcategory[] = [];

  private locationRange: LocationRange;
  private categoryId: number;
  private subcategoryId: number;

  constructor(
    private culturalOffersService: CulturalOffersService,
    private categoriesService: CategoriesService,
    private subcategoriesService: SubcategoriesService
  ) { 
    this.locationRange = {
      latitudeFrom: 41.0,
      latitudeTo: 47.0,
      longitudeFrom: 18.0,
      longitudeTo: 23.0
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
    this.categoriesService.getCategories().subscribe(categories => this.categories = categories);
  }

  getSubcategories(category: Category): void {
    this.subcategoriesService.getSubcategories(category.id).subscribe(subcategories => this.subcategories = subcategories);
  }

  updateFilters(event: CulturalOfferLocationsFilter) {
    if (event.category)
      this.categoryId = event.category.id;
    if (event.subcategory)
      this.subcategoryId = event.subcategory.id;
    this.getCulturalOfferLocations();
  }

  updateLocations(event: LocationRange): void {
    this.locationRange = event;
    this.getCulturalOfferLocations();
  }

}
