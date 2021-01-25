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

    selectedFiles: FileList;

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
    }

    setUp() {
        this.getCategories();
        if(this.model) {
            this.culturalOffer = {...this.model};
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

    isSubcatDisabled():boolean {
        return this.tempCategoryId === null;
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

    appendFile() {

    }

    selectFiles(event:any): void {
        this.selectedFiles = event.target.files;
    }

    onSubmit(): void {
        this.imageService.addImages(this.imageFormData())
        .subscribe(imageIds => {
            this.formSubmitted.emit(this.makeOffer(imageIds));
        });  
    }

    imageFormData(): FormData {
        let imageData = new FormData();

        if(this.selectedFiles)
            for (let i = 0; i < this.selectedFiles.length; i++) {
                imageData.append('images', this.selectedFiles[i]);
            }
        else
            imageData.append('images', '');

        return imageData;
    }

    makeOffer(imageIds:number[]): CulturalOfferToAdd {

        let offerToAdd:CulturalOfferToAdd;

        this.geocodeService.geocode(this.culturalOffer.address)
        .subscribe(range => {
            offerToAdd = {
                id: this.culturalOffer.id,
                name: this.culturalOffer.name,
                description: this.culturalOffer.description,
                subcategory: this.culturalOffer.subcategory,
                longitude: range.longitudeFrom + (range.longitudeTo - range.longitudeFrom)/2,
                latitude: range.latitudeFrom + (range.latitudeTo - range.latitudeFrom)/2,
                address: this.culturalOffer.address,
                images: imageIds
            };
        });

        return offerToAdd;
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
        if(!this.selectedFiles) {
            return true;
        }
        return false;
    }

    errorMessage() {
        return "Name is required!"
    }

}