import { Role } from 'src/app/modules/authentication/role';
import { User } from 'src/app/modules/authentication/user';
import { Category } from 'src/app/modules/categories/category';
import { CulturalOffer, CulturalOfferLocation, LocationRange } from 'src/app/modules/cultural-offers/cultural-offer';
import { News, NewsPage } from 'src/app/modules/news/news';
import { Review, ReviewPage } from 'src/app/modules/reviews/review';
import { Subcategory } from 'src/app/modules/subcategories/subcategory';

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
        images: ['image1', 'image2']
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
        images: ['image1', 'image2']
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
  mockNewsPage,
  mockReviewPage,
  mockUser,
  mockAdmin,
  mockMapBounds,
  mockLocationRange
};
