export interface Category {
  id: number,
  name: string
};

export interface CategoriesPage {
  data: Category[],
  links: Map<String, String>
}
