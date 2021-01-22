export interface Review {
  id: number;
  culturalOfferId: number;
  rating: number;
  author: ReviewAuthor;
  comment: string;
  images: string[];
}

export interface ReviewAuthor {
  id: number;
  firstName: string;
  lastName: string;
}

export interface ReviewPage {
  data: Review[];
  links: Map<string, string>;
}
