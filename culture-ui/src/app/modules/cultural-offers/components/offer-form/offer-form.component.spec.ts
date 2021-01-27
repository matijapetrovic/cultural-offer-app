import { HttpClientTestingModule } from "@angular/common/http/testing";
import { DebugElement } from "@angular/core";
import { async, ComponentFixture, fakeAsync, TestBed, tick } from "@angular/core/testing";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { By } from "@angular/platform-browser";
import { GeolocationService } from "src/app/core/services/geolocation.service";
import { ImageService } from "src/app/core/services/image.service";
import { CategoriesService } from "src/app/modules/categories/categories.service";
import { SubcategoriesService } from "src/app/modules/subcategories/subcategories.service";
import { mockCategoryNames, mockCulturalOfferView, mockSubcategoryNames } from "src/app/shared/testing/mock-data";
import { OfferFormComponent } from "./offer-form.component";


describe('OfferFormComponent', () => {

    let component:OfferFormComponent;
    let fixture: ComponentFixture<OfferFormComponent>;

    beforeEach(async () => {

        const categoriesServiceMock = {
            getCategoryNames: jasmine.createSpy('getCategoryNames')
              .and.returnValue({subscribe: () => {
                component.categories = mockCategoryNames
            }})
          };

          const subcategoriesServiceMock = {
            getSubcategoryNames: jasmine.createSpy('getSubcategoryNames')
              .and.returnValue({subscribe: () => {
                component.subcategories = mockSubcategoryNames;
              }})
          };

          const imageServiceMock = {
            addImages: jasmine.createSpy('addImages')
              .and.returnValue({subscribe: () => {
                component.updateImagesIds([7, 9, 11]);
                component.returnOfferWithLocation();
            }})
          };

          const geocodeServiceMock = {
            geocode: jasmine.createSpy('geocode')
              .and.returnValue({subscribe: () => {
                component.updateLocation({
                    latitudeFrom: 1,
                    latitudeTo: 10,
                    longitudeFrom: 2,
                    longitudeTo: 20
                });
                component.returnOffer();
            }})
          };

          await TestBed.configureTestingModule({
            declarations: [OfferFormComponent],
            imports: [
              FormsModule,
              ReactiveFormsModule,
              HttpClientTestingModule],
            providers: [{ provide: CategoriesService, useValue: categoriesServiceMock},
                        { provide: SubcategoriesService, useValue: subcategoriesServiceMock },
                        { provide: ImageService, useValue: imageServiceMock},
                        { provide: GeolocationService, useValue: geocodeServiceMock }]
          })
          .compileComponents();

    });

    beforeEach(() => {
        fixture = TestBed.createComponent(OfferFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('setUp() should copy model if it is passed as input', fakeAsync(() => {

        component.model = mockCulturalOfferView;

        spyOn(component, 'getSubcategories');
        spyOn(component, 'getCategories');
        component.setUp();

        tick();

        expect(component.getSubcategories).toHaveBeenCalled();
        expect(component.getCategories).toHaveBeenCalled();

        expect(component.culturalOffer).toBeTruthy();
        expect(component.culturalOffer.id).toEqual(component.model.id);
        expect(component.culturalOffer.name).toEqual(component.model.name);
        expect(component.culturalOffer.description).toEqual(component.model.description);
        expect(component.culturalOffer.address).toEqual(component.model.address);

        expect(component.tempCategoryId).toEqual(component.culturalOffer.subcategory.categoryId);

        fixture.whenStable()
        .then(() => {
            expect(component.categories.length).toBe(2);
            expect(component.subcategories.length).toBe(2);
            fixture.detectChanges(); // synchronize HTML with component data        
            expect(component.ngForm.controls.category.value).toBeTruthy();
            expect(component.ngForm.controls.subcategory).toBeTruthy();
        });      

    }));

    it('setUp() should set up model if it is not passed as input', fakeAsync(() => {
        spyOn(component, 'getCategories');
        component.setUp();

        expect(component.getCategories).toHaveBeenCalled();

        expect(component.culturalOffer).toBeTruthy();
        expect(component.culturalOffer.id).toEqual(null);

        tick();

        expect(component.categories.length).toBe(2);
        // fixture.detectChanges(); // synchronize HTML with component data        
        // expect(component.ngForm.controls.category.value.id).toBeFalsy();
        // expect(component.ngForm.controls.subcategory.value.id).toBeFalsy();     

    }));

    it('getCategories() should update categories list', fakeAsync(() => {

        component.categories = [];
        component.getCategories();

        tick();

        expect(component.categories.length).toBe(2);
    }));

    it('getSubcategories() should update categories list', fakeAsync(() => {

        component.subcategories = [];
        component.getSubcategories();

        tick();

        expect(component.subcategories.length).toBe(2);
    }));

    // it('appendFile() shpuld add new image src and update image viev', fakeAsync(() => {

    //     component.culturalOffer = mockCulturalOfferView;

    //     let filesLenght = component.newImages.length;
    //     let srcsLenght = component.culturalOffer.images.length;

    //     let prevElements: DebugElement[] = 
    //             fixture.debugElement.queryAll(By.css('.img-show'));
    //     let prevElementsLen = prevElements.length;

    //     let event = {
    //         target: {
    //             files: [
    //                 {}
    //             ],
    //             result: ''
    //         }
    //     }

    //     component.appendFile(event);

    //     fixture.whenStable()
    //     .then(() => {
    //         expect(component.newImages.length).toBe(filesLenght + 1);
    //         expect(component.culturalOffer.images.length).toBe(srcsLenght + 1);
    //         fixture.detectChanges();
    //         let elements: DebugElement[] = 
    //             fixture.debugElement.queryAll(By.css('.img-show'));
    //         expect(elements.length).toBe(prevElementsLen + 1); 
    //     });

    // }))

    it('removeImage() should ', async () => {

        component.culturalOffer = mockCulturalOfferView;

        fixture.detectChanges();

        let imgLenght = component.culturalOffer.images.length;
        let idsLenght = component.culturalOffer.imagesIds.length;
        
        // let prevElements: DebugElement[] = 
        //          fixture.debugElement.queryAll(By.css('.img-show'));
        // let prevElementsLen = prevElements.length;

        component.removeImage(component.culturalOffer.images[0]);

        expect(component.culturalOffer.images.length).toBe(imgLenght - 1);
        expect(component.culturalOffer.imagesIds.length).toBe(idsLenght - 1);
        fixture.detectChanges();
        // let elements: DebugElement[] = 
        //                 fixture.debugElement.queryAll(By.css('.img-show'));
        // expect(elements.length).toBe(prevElementsLen - 1); 
    
    });

    it('onSubmit() shoud post new images if there are some', fakeAsync(() => {

        component.newImages = [new File([""], "...")];

        spyOn(component, 'updateImagesIds');
        spyOn(component, 'returnOfferWithLocation');

        component.onSubmit();

        tick();

        expect(component.updateImagesIds).toHaveBeenCalled();
        expect(component.returnOfferWithLocation).toHaveBeenCalled();

    }));


    it('onSubmit() should not post new images if there are no some', fakeAsync(() => {

        component.newImages = [];

        spyOn(component, 'returnOfferWithLocation');

        component.onSubmit();

        tick();

        expect(component.returnOfferWithLocation).toHaveBeenCalled();

    }));

    it('updateImagesIds() should concat imageIds array with new array', () => {

        let lenght = component.culturalOffer.imagesIds.length;

        component.updateImagesIds([1, 2, 3]);

        expect(component.culturalOffer.imagesIds.length).toBe(lenght + 3);
    });

    it('returnOfferWithLocation() should get new location if address is diffrent', fakeAsync(() => {

        component.model = mockCulturalOfferView;

        component.culturalOffer.address = component.model.address + 'a';

        spyOn(component, 'updateLocation');
        spyOn(component, 'returnOffer');

        component.onSubmit();

        tick();

        expect(component.updateLocation).toHaveBeenCalled();
        expect(component.returnOffer).toHaveBeenCalled();

    }));

    it('returnOfferWithLocation() should not get new location if address is the same', fakeAsync(() => {

        component.model = mockCulturalOfferView;

        component.culturalOffer.address = component.model.address;

        spyOn(component, 'returnOffer');

        component.onSubmit();

        tick();

        expect(component.returnOffer).toHaveBeenCalled();

    }));

    it('updateRange() shold change longitude and latitude', () => {

        component.culturalOffer.longitude = 1;
        component.culturalOffer.latitude = 2;


        component.updateLocation({
            latitudeFrom: 1,
            latitudeTo: 10,
            longitudeFrom: 2,
            longitudeTo: 20
        });

        expect(component.culturalOffer.longitude).not.toBe(1);
        expect(component.culturalOffer.latitude).not.toBe(2);
    });

    it('invalidFormInputs() to return true when inputs are invalid', () => {

        component.culturalOffer = mockCulturalOfferView;
        component.culturalOffer.name = '';

        expect(component.invalidFormInputs()).toBeTruthy();
    })

    it('invalidFormInputs() to return false when inputs are valid', () => {

        component.culturalOffer = mockCulturalOfferView;

        expect(component.invalidFormInputs()).toBeFalsy();
    });

    it(`errorMessage() should return 'Name is required!'`, () => {
        const message = 'Name is required!';
    
        expect(component.errorMessage()).toEqual(message);
    });

});