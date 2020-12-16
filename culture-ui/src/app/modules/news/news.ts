export interface News {
  id: number,
  culturalOfferId: number,
  title: String,
  postedDate: String,
  authorId: number,
  text: String,
  images: String[]
}

export interface NewsPage {
  data: News[],
  links: Map<String, String>
}