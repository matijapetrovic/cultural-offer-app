export interface News {
  id: number;
  culturalOfferId: number;
  title: string;
  author: NewsAuthor;
  postedDate: number[];
  text: string;
  images: string[];
}

export interface NewsAuthor {
  id: number;
  firstName: string;
  lastName: string;
}
export interface NewsPage {
  data: News[];
  links: Map<string, string>;
}
