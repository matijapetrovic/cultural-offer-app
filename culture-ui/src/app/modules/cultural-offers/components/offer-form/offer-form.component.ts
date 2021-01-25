import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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

    submitted:boolean;

    addForm: FormGroup;

    categories: Category[];
    subcategories: Subcategory[];

    selectedFiles: FileList;
    culturalOffer: CulturalOfferView;
    tempCategoryId: number;

    constructor(
        private formBuilder: FormBuilder,
        private categoriesService: CategoriesService,
        private subcategoriesService: SubcategoriesService,
        private imageService: ImageService,
        private geocodeService: GeolocationService
    ) { 
        this.submitted = false;
    }

    ngOnInit(): void {
        this.addForm = this.formBuilder.group({
            name: ['', Validators.required],
            description: ['', Validators.required],
            category: new FormControl({value: null}, Validators.required),
            subcategory: new FormControl({value: null}, Validators.required),
            images: ['', Validators.required],
            address:  ['', Validators.required]
        });
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
        this.f.subcategory.reset();
        this.f.subcategory.enable();
        this.getSubcategories();
    }

    getSubcategories(): void {
        this.subcategoriesService
        .getSubcategoryNames(this.tempCategoryId)
        .subscribe(subcategories => this.subcategories = subcategories);        
    }

    selectFiles(event:any): void {
        this.selectedFiles = event.target.files;
    }

    onSubmit(): void {
        this.submitted = true;
        
        this.imageService.addImages(this.imageFormData())
        .subscribe(imageIds => {
            this.removeFormInputs();
            this.formSubmitted.emit(this.makeOffer(imageIds));
        });  
    }

    imageFormData(): FormData {
        let imageData = new FormData();

        for (let i = 0; i < this.selectedFiles.length; i++) {
            imageData.append('images', this.selectedFiles[i]);
        }

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

    get f() { return this.addForm.controls; }

    invalidFormInputs(): boolean {
        if (this.f.name.value === '' || this.f.name.value === null) {
            return true;
        }
        if (this.f.description.value === '' || this.f.description.value === null) {
            return true;
        }
        if (this.f.category.value ===  null) {
            return true;
        }
        if (this.f.subcategory.value === null) {
            return true;
        }
        if (this.f.images.value === null) {
            return true;
        }
        if (this.f.address.value === '' || this.f.address.value === null) {
            return true;
        }
        return false;
    }

    removeFormInputs() {
        this.addForm.reset();
    }

    errorMessage() {
        return "Name is required!"
    }

}