import { CulturalOffer } from "../cultural-offers/cultural-offer";

export interface News {
  id: number;
  culturalOfferId: number;
  title: string;
  author: NewsAuthor;
  postedDate: number[];
  text: string;
  images: string[];
}

export interface NewsToAdd {
  id: number;
  culturalOfferId: number;
  title: string;
  text: string;
  images: number[];
}

export interface NewsView {
  id: number;
  culturalOfferId: number;
  title: string;
  text: string;
  images: string[];
  imagesIds: number[];
}

export interface NewsAuthor {
  id: number;
  firstName: string;
  lastName: string;
}
export interface NewsPage {
  data: NewsView[];
  links: Map<string, string>;
}
