export interface Category {
  id: string,
  name: string
}

export interface CategoriesPage {
  data: Category[],
  links: Map<String, String>
}
