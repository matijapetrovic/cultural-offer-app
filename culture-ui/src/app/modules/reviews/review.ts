export interface Review {
  id: number,
  culturalOfferId: number,
  comment: String,
  images: String[]
}

export interface ReviewPage {
  data: Review[],
  links: Map<String, String>
}