export interface Subcategory {
  id: number,
  categoryId: number,
  name: string
};

export interface SubcategoriesPage {
  data: Subcategory[],
  links: Map<String, String>
}