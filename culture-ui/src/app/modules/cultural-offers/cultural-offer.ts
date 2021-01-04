import { Category } from "../categories/category";
import { Subcategory } from "../subcategories/subcategory";

export interface CulturalOffer {
  id: number,
  name: string,
  description: string,
  images: Array<string>,
  latitude: number,
  longitude: number,
  subscribed?: boolean
};

export interface CulturalOfferLocation {
  id: number,
  name: string,
  location: Location
};

export interface Location {
  latitude: number,
  longitude: number,
  address: string
};

export interface LocationRange {
  latitudeFrom: number,
  latitudeTo: number,
  longitudeFrom: number,
  longitudeTo: number
};

export interface CulturalOfferLocationsFilter {
  category: Category,
  subcategory: Subcategory 
};