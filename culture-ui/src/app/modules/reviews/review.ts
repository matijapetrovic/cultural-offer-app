export interface Review {
  id: number;
  culturalOfferId: number;
  rating: number;
  author: ReviewAuthor;
  comment: string;
  images: string[];
  reply?: Reply;
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

export interface ReviewToAdd {
  comment: string;
  rating: number;
  images: number[];
}

export interface Reply {
  comment: string;
  author: ReplyAuthor;
}

export interface ReplyAuthor {
  id: number;
  firstName: string;
  lastName: string;
}

export interface ReplyToAdd {
  comment: string;
}
