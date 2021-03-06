import { Category } from '../categories/category';
import { Subcategory } from '../subcategories/subcategory';

export interface CulturalOfferToAdd {
  id: number;
  name: string;
  description: string;
  latitude: number;
  longitude: number;
  images: number[];
  subcategoryId: number;
  categoryId: number;
  address: string;
}

export interface CulturalOfferView {
  id: number;
  name: string;
  description: string;
  subcategory: Subcategory;
  images: string[];
  imagesIds: number[];
  latitude: number;
  longitude: number;
  address: string;
}


export interface CulturalOffer {
  id: number;
  name: string;
  description: string;
  rating: number;
  images: string[];
  reviewCount: number;
  latitude: number;
  longitude: number;
}

export interface CulturalOfferLocation {
  id: number;
  name: string;
  location: Location;
}

export interface Location {
  latitude: number;
  longitude: number;
  address: string;
}

export interface LocationRange {
  latitudeFrom: number;
  latitudeTo: number;
  longitudeFrom: number;
  longitudeTo: number;
}

export interface CulturalOfferLocationsFilter {
  category: Category;
  subcategory: Subcategory;
}

export interface CulturalOffersPage {
  data: CulturalOfferView[];
  links: Map<string, string>;
}
