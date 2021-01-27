import { Role } from 'src/app/modules/authentication/role';
import { User } from 'src/app/modules/authentication/user';
import { CategoriesPage, Category } from 'src/app/modules/categories/category';
import { CulturalOffer, CulturalOfferLocation, CulturalOffersPage, CulturalOfferToAdd, CulturalOfferView, LocationRange } from 'src/app/modules/cultural-offers/cultural-offer';
import { SubscribedOfferPage, SubscribedSubcategoriesPage } from 'src/app/modules/dashboard/subscriptions-details';
import { News, NewsPage } from 'src/app/modules/news/news';
import { ReplyToAdd, Review, ReviewPage, ReviewToAdd } from 'src/app/modules/reviews/review';
import { SubcategoriesPage, Subcategory } from 'src/app/modules/subcategories/subcategory';

const mockCategoryNames: Category[] = [
  {
    id: 1,
    name: 'Cat 1'
  },
  {
    id: 2,
    name: 'Cat 2'
  }
];

const mockSubcategoryNames: Subcategory[] = [
  {
    id: 1,
    name: 'Subcat 1',
    categoryId: 1,
  },
  {
    id: 1,
    name: 'Subcat 2',
    categoryId: 1
  }
];

const mockSubcategoriesPage: SubcategoriesPage = {
  data: [
    {
      id: 1,
      name: 'Subcategory1',
      categoryId: 1
    },
    {
      id: 2,
      name: 'Subcategory2',
      categoryId: 1
    },
  ],
  links: new Map([['next', 'next-link'], ['self', 'self-link']])
};

const mockCulturalOffersPage: CulturalOffersPage = {
  data: [
    {
      id: 1,
      name: "Offer1",
      description: "Description",
      subcategory: {
        id: 1,
        name: 'Subcategory1',
        categoryId: 1
      },
      images: ["img1", "img2"],
      imagesIds: [1, 2],
      latitude: 2.0,
      longitude: 2.3,
      address: "address"
    },
    {
      id: 2,
      name: "Offer2",
      description: "Description",
      subcategory: {
        id: 1,
        name: 'Subcategory2',
        categoryId: 1
      },
      images: ["img1", "img2"],
      imagesIds: [1, 2],
      latitude: 2.0,
      longitude: 2.3,
      address: "address"
    },
    {
      id: 1,
      name: "Offer3",
      description: "Description",
      subcategory: {
        id: 1,
        name: 'Subcategory3',
        categoryId: 1
      },
      images: ["img1", "img2"],
      imagesIds: [1, 2],
      latitude: 2.0,
      longitude: 2.3,
      address: "address"
    }
  ],
  links: new Map([['next', 'next-link'], ['self', 'self-link']])
};

const mockCulturalOffer: CulturalOffer = {
    id: 1,
    name: 'Offer 1',
    description: 'Some description',
    rating: 3.0,
    reviewCount: 2,
    images: ['1', '2'],
    latitude: 45.0,
    longitude: 45.0
};

const mockCulturalOfferToAdd: CulturalOfferToAdd = {
  id: 1,
  name: 'Offer 1',
  description: 'Some description',
  latitude: 45.0,
  longitude: 45.0,
  images: [1, 2],
  subcategoryId: 1,
  categoryId: 1,
  address: 'address'
};

const mockCulturalOfferView: CulturalOfferView = {
  id: 1,
  name: 'Offer 1',
  description: 'Some description',
  subcategory: {
    id: 1,
    name: 'Subcategory3',
    categoryId: 1
  },
  imagesIds: [1, 2],
  images: ['1', '2'],
  latitude: 45.0,
  longitude: 45.0,
  address: "address"
}

const mockOfferLocations: CulturalOfferLocation[] = [
    {
      id: 1,
      name: 'Offer 1',
      location: {
        latitude: 40.0,
        longitude: 40.0,
        address: 'Address 1'
      }
    },
    {
      id: 2,
      name: 'Offer 2',
      location: {
        latitude: 41.0,
        longitude: 41.0,
        address: 'Address 2'
      }
    }
  ];

const mockNews: News = {
    id: 1,
    culturalOfferId: 1,
    title: 'Some title',
    postedDate: [1970, 1, 1],
    author: {
        id: 1,
        firstName: 'First name author',
        lastName: 'Last name author'
    },
    text: 'Some text',
    images: ['image1', 'image2']
    };

const mockNewsPage: NewsPage = {
    data: [
        {
        id: 1,
        culturalOfferId: 1,
        title: 'Some title',
        postedDate: [1970, 1, 1],
        author: {
            id: 1,
            firstName: 'First name author',
            lastName: 'Last name author'
        },
        text: 'Some text',
        images: ['image1', 'image2'],
        imagesIds: [1, 2]
        },
        {
        id: 2,
        culturalOfferId: 1,
        title: 'Some title 2',
        postedDate: [1970, 1, 1],
        author: {
            id: 2,
            firstName: 'First name author 2',
            lastName: 'Last name author 2'
        },
        text: 'Some other text',
        images: ['image1', 'image2'],
        imagesIds: [1, 2]
        }
    ],
    links: new Map([['next', 'next-link'], ['self', 'self-link']])
};

const mockReview: Review =  {
    id: 1,
    culturalOfferId: 1,
    rating: 3.0,
    author: {
      id: 1,
      firstName: 'First name author',
      lastName: 'Last name author'
    },
    comment: 'Some text',
    images: ['image1', 'image2']
  };

const mockReplyToAdd: ReplyToAdd = {
  comment: 'Some comment'
};

const mockEmptyCommentReplyToAdd: ReplyToAdd = {
  comment: ''
};

const mockReviewToAdd: ReviewToAdd = {
  comment: 'Some comment',
  rating: 5,
  images: [
    1,
    2,
    3
  ]
};

const mockEmptyCommentReviewToAdd: ReviewToAdd = {
  comment: '',
  rating: 5,
  images: [
    1,
    2,
    3
  ]
};

const mockNegativeRatingReviewToAdd: ReviewToAdd = {
  comment: 'Some comment',
  rating: -5,
  images: [
    1,
    2,
    3
  ]
};

const mockEmptyReviewPage: ReviewPage = {
  data: [],
  links: new Map([['prev', 'prev-link'], ['self', 'self-link']])
};

const mockReviewPage: ReviewPage = {
    data: [
      {
        id: 1,
        culturalOfferId: 1,
        rating: 3.0,
        author: {
          id: 1,
          firstName: 'First name author',
          lastName: 'Last name author'
        },
        comment: 'Some text',
        images: ['image1', 'image2']
      },
      {
        id: 2,
        culturalOfferId: 1,
        rating: 4.0,
        author: {
          id: 2,
          firstName: 'First name author 2',
          lastName: 'Last name author 2'
        },
        comment: 'Some other text',
        images: ['image1', 'image2']
      }
    ],
    links: new Map([['next', 'next-link'], ['self', 'self-link']])
  };

const mockCategoriesPage: CategoriesPage = {
  data: [
    {
      id: 1,
      name: 'Category1'
    },
    {
      id: 2,
      name: 'Category2'
    },
  ],
  links: new Map([['next', 'next-link'], ['self', 'self-link']])
};

const mockUser: User = {
  id: 1,
  username: 'user',
  password: 'password',
  firstName: 'name',
  lastName: 'last name',
  role: [Role.ROLE_USER]
};

const mockAdmin: User = {
  id: 1,
  username: 'user',
  password: 'password',
  firstName: 'name',
  lastName: 'last name',
  role: [Role.ROLE_ADMIN]
};

const validLocation = 'Location';
const invalidLocation = 'Not valid';
const invalidLocationMessage = { severity: 'warn', summary: 'Error!', detail: 'Location not found' };

const mockMapBounds: google.maps.LatLngBounds =
  new google.maps.LatLngBounds({lat: 30.0, lng: 30.0}, {lat: 45.0, lng: 45.0});

const mockLocationRange: LocationRange = {
  latitudeFrom: 30.0,
  longitudeFrom: 30.0,
  latitudeTo: 45.0,
  longitudeTo: 45.0
};


const mockSubscribedSubcategoriesPage: SubscribedSubcategoriesPage = {
  data: [
    {
      id: 1,
      categoryId: 1,
      name: 'Some name'
    },
    {
      id: 2,
      categoryId: 1,
      name: 'Some name 2'
    }
  ],
  links: new Map([['next', 'next-link'], ['self', 'self-link']])
};

const mockEmptySubscribedSubcategoriesPage: SubscribedSubcategoriesPage = {
  data: [],
  links: new Map([['prev', 'prev-link'], ['self', 'self-link']])
};

const mockSubscribedOffersPage: SubscribedOfferPage = {
  data: [
    {
      id: 1,
      name: 'Some name',
      description: 'Some description',
      images: [
        'some image link',
        'some image link',
        'some image link',
      ]
    },
    {
      id: 2,
      name: 'Some name 2',
      description: 'Some description 2',
      images: [
        'some image link 2',
        'some image link 2',
        'some image link 2',
      ]
    }
  ],
  links: new Map([['next', 'next-link'], ['self', 'self-link']])
};

const mockEmptySubscribedOffersPage: SubscribedOfferPage = {
  data: [],
  links: new Map([['prev', 'prev-link'], ['self', 'self-link']])
};

const mockImage1: File = new File([''], 'image1.png');
const mockImage2: File = new File([''], 'image2.png');

const mockImagesToAdd: FormData = new FormData();
mockImagesToAdd.append('images', mockImage1);
mockImagesToAdd.append('images', mockImage2);

const mockEmptyImagesToAdd: FormData = new FormData();

export {
  invalidLocationMessage,
  validLocation,
  invalidLocation,
  mockCategoryNames,
  mockSubcategoryNames,
  mockCulturalOffer,
  mockOfferLocations,
  mockNews,
  mockReview,
  mockReplyToAdd,
  mockEmptyCommentReplyToAdd,
  mockNewsPage,
  mockReviewPage,
  mockEmptyReviewPage,
  mockReviewToAdd,
  mockEmptyCommentReviewToAdd,
  mockNegativeRatingReviewToAdd,
  mockUser,
  mockAdmin,
  mockMapBounds,
  mockLocationRange,
  mockCategoriesPage,
  mockSubcategoriesPage,
  mockCulturalOffersPage,
  mockCulturalOfferToAdd,
  mockCulturalOfferView,

  mockSubscribedSubcategoriesPage,
  mockEmptySubscribedSubcategoriesPage,
  mockSubscribedOffersPage,
  mockEmptySubscribedOffersPage,
  mockImagesToAdd,
  mockEmptyImagesToAdd,
  mockImage1,
  mockImage2
};
