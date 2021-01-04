export interface Review {
  id: number,
  culturalOfferId: number,
  rating: number,
  author: ReviewAuthor;
  comment: String,
  images: String[]
}

export interface ReviewAuthor {
  id: number,
  firstName: string,
  lastName: string
}

export interface ReviewPage {
  data: Review[],
  links: Map<String, String>
}