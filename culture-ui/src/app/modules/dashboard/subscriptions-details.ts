export interface SubscriptionsDetails {
}

export interface SubscriptionsSubcategories {
    id: number;
    categoryId: number;
    name: string;
}

export interface SubscriptionsSubcategoriesPage {
    data: SubscriptionsSubcategories[];
    links: Map<string, string>;
}
