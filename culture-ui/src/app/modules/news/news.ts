export interface News {
  id: number,
  culturalOfferId: number,
  title: String,
  author: NewsAuthor,
  postedDate: String,
  text: String,
  images: String[]
}

export interface NewsAuthor {
  id: number,
  firstName: string,
  lastName: string
}
export interface NewsPage {
  data: News[],
  links: Map<String, String>
}