export interface SubscriptionsDetails {
}

export interface SubscribedSubcategory {
    id: number;
    categoryId: number;
    name: string;
}

export interface SubscribedSubcategoriesPage {
    data: SubscribedSubcategory[];
    links: Map<string, string>;
}

export interface SubscribedOffer {
    id: number;
    name: string;
    description: string;
    images: string[];
}
