import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Category } from 'src/app/modules/categories/category';
import { Subcategory } from 'src/app/modules/subcategories/subcategory';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { SubcategoriesService } from 'src/app/modules/subcategories/subcategories.service';
import { CulturalOfferToAdd, CulturalOfferView } from 'src/app/modules/cultural-offers/cultural-offer'
import { ImageService } from 'src/app/core/services/image.service';
import { GeolocationService } from 'src/app/core/services/geolocation.service';

@Component({
    selector: 'app-offer-form',
    templateUrl: './offer-form.component.html',
    styleUrls: ['./offer-form.component.scss']
})
export class OfferFormComponent implements OnInit {

    @Input()
    model: CulturalOfferView;

    @Output()
    formSubmitted = new EventEmitter<CulturalOfferToAdd>();

    @ViewChild('subcategorySelect') subcategorySelect;

    categories: Category[];
    subcategories: Subcategory[];

    newImages: Array<File>;

    culturalOffer: CulturalOfferView;
    tempCategoryId: number;

    constructor(
        private categoriesService: CategoriesService,
        private subcategoriesService: SubcategoriesService,
        private imageService: ImageService,
        private geocodeService: GeolocationService
    ) { 
    }

    ngOnInit(): void {
        this.setUp(); 
        this.newImages = new Array<File>();
    }

    setUp() {
        this.getCategories();
        if(this.model) {
            this.culturalOffer = JSON.parse(JSON.stringify(this.model));
            this.tempCategoryId = this.culturalOffer.subcategory.categoryId;
            this.getSubcategories();
        }
        else {
            this.model = {
                id: null,
                name: '',
                description: '',
                subcategory: {
                    id: null,
                    categoryId: null,
                    name: '',
                },
                images: [],
                imagesIds: [],
                latitude: null,
                longitude: null,
                address: ''
            };
            this.culturalOffer = {...this.model};
            this.tempCategoryId = null;
        }
    }

    getCategories():void {
        this.categoriesService
        .getCategoryNames()
        .subscribe(categories => this.categories = categories);
    }

    categoryChanged(): void {
        this.subcategorySelect.handleClearClick();
        this.getSubcategories();
    }

    getSubcategories(): void {
        this.subcategoriesService
        .getSubcategoryNames(this.tempCategoryId)
        .subscribe(subcategories => this.subcategories = subcategories);        
    }

    appendFile(event: any) {
        this.newImages.push(event.target.files.item(0));

        var reader = new FileReader();

        reader.onload = (event:any) => {
            this.culturalOffer.images.push(event.target.result);
        }

        reader.readAsDataURL(event.target.files[0]);
    }

    removeImage(imageUrl: string) {
        let index:number = this.culturalOffer.images.indexOf(imageUrl);
        this.culturalOffer.images.splice(index, 1);

        if(index < this.culturalOffer.imagesIds.length) {
            this.culturalOffer.imagesIds.splice(index, 1);
        }
        else {
            this.newImages.splice(index - this.culturalOffer.imagesIds.length, 1);
        }
    }

    onSubmit(): void {

        if(this.newImages.length > 0) {
            this.imageService.addImages(this.imageFormData())
                .subscribe(imageIds => {
                this.updateImagesIds(imageIds);
                this.returnOfferWithLocation();
            });
        } else {
            this.returnOfferWithLocation();
        } 
    }

    updateImagesIds(imagesIds: number[]) {
        this.culturalOffer.imagesIds = this.culturalOffer.imagesIds.concat(imagesIds);
    }

    imageFormData(): FormData {
        let imageData = new FormData();

        for (let i = 0; i < this.newImages.length; i++) {
            imageData.append('images', this.newImages[i]);
        }

        return imageData;
    }

    returnOfferWithLocation() {

        if(this.culturalOffer.address !== this.model.address) {
            this.geocodeService.geocode(this.culturalOffer.address)
            .subscribe(range => {
               this.updateLocation(range);
               this.returnOffer();
            });
        } else {
            this.returnOffer();
        }
    }

    updateLocation(range:any) {
        this.culturalOffer.longitude = range.longitudeFrom + (range.longitudeTo - range.longitudeFrom)/2;
        this.culturalOffer.latitude = range.latitudeFrom + (range.latitudeTo - range.latitudeFrom)/2;
    }

    returnOffer() {
        
        let retVal:CulturalOfferToAdd = {
            id: this.culturalOffer.id,
            name: this.culturalOffer.name,
            description: this.culturalOffer.description,
            subcategoryId: this.culturalOffer.subcategory.id,
            categoryId: this.tempCategoryId,
            longitude: this.culturalOffer.longitude,
            latitude: this.culturalOffer.latitude,
            address: this.culturalOffer.address,
            images: this.culturalOffer.imagesIds
        };

        this.formSubmitted.emit(retVal);
    }

    invalidFormInputs(): boolean {
        if (this.culturalOffer.name === '' || this.culturalOffer.name === null) {
            return true;
        }
        if (this.culturalOffer.description === '' || this.culturalOffer.description === null) {
            return true;
        }
        if (this.culturalOffer.subcategory === null || this.culturalOffer.subcategory.id === null) {
            return true;
        }
        if (this.culturalOffer.address === '' || this.culturalOffer.address === null) {
            return true;
        }
        return false;
    }

    errorMessage() {
        return "Name is required!"
    }

}