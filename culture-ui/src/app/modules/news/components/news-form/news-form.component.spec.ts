import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DebugElement } from '@angular/core';
import { async, ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { GeolocationService } from 'src/app/core/services/geolocation.service';
import { ImageService } from 'src/app/core/services/image.service';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { SubcategoriesService } from 'src/app/modules/subcategories/subcategories.service';
import { mockCategoryNames, mockCulturalOfferView, mockNewsView, mockSubcategoryNames } from 'src/app/shared/testing/mock-data';
import { NewsFormComponent } from './news-form.component';


describe('NewsFormComponent', () => {

    let component: NewsFormComponent;
    let fixture: ComponentFixture<NewsFormComponent>;

    beforeEach(async () => {

          const imageServiceMock = {
            addImages: jasmine.createSpy('addImages')
              .and.returnValue({subscribe: () => {
                component.updateImagesIds([7, 8, 9]);
                component.returnNews();
            }})
          };


          await TestBed.configureTestingModule({
            declarations: [NewsFormComponent],
            imports: [
              FormsModule,
              ReactiveFormsModule,
              HttpClientTestingModule],
            providers: [{ provide: ImageService, useValue: imageServiceMock}]
          })
          .compileComponents();

    });

    beforeEach(() => {
        fixture = TestBed.createComponent(NewsFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('setUp() should copy model if it is passed as input', fakeAsync(() => {

        component.model = mockNewsView;

        component.setUp();

        expect(component.news).toBeTruthy();
        expect(component.news.id).toEqual(component.model.id);
        expect(component.news.title).toEqual(component.model.title);
        expect(component.news.culturalOfferId).toEqual(component.model.culturalOfferId);
        expect(component.news.text).toEqual(component.model.text);

    }));

    it('setUp() should set up model if it is not passed as input', fakeAsync(() => {

        component.setUp();

        tick();

        expect(component.news).toBeTruthy();
        expect(component.news.id).toEqual(null);

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

        component.news = mockNewsView;

        fixture.detectChanges();

        const imgLenght = component.news.images.length;
        const idsLenght = component.news.imagesIds.length;

        // let prevElements: DebugElement[] =
        //          fixture.debugElement.queryAll(By.css('.img-show'));
        // let prevElementsLen = prevElements.length;

        component.removeImage(component.news.images[0]);

        expect(component.news.images.length).toBe(imgLenght - 1);
        expect(component.news.imagesIds.length).toBe(idsLenght - 1);
        fixture.detectChanges();
        // let elements: DebugElement[] =
        //                 fixture.debugElement.queryAll(By.css('.img-show'));
        // expect(elements.length).toBe(prevElementsLen - 1);

    });

    it('onSubmit() shoud post new images if there are some', fakeAsync(() => {

        component.newImages = [new File([''], '...')];

        spyOn(component, 'updateImagesIds');
        spyOn(component, 'returnNews');

        component.onSubmit();

        tick();

        expect(component.updateImagesIds).toHaveBeenCalled();
        expect(component.returnNews).toHaveBeenCalled();

    }));


    it('onSubmit() should not post new images if there are no some', fakeAsync(() => {

        component.newImages = [];

        spyOn(component, 'returnNews');

        component.onSubmit();

        tick();

        expect(component.returnNews).toHaveBeenCalled();

    }));

    it('updateImagesIds() should concat imageIds array with new array', () => {

        const lenght = component.news.imagesIds.length;

        component.updateImagesIds([1, 2, 3]);

        expect(component.news.imagesIds.length).toBe(lenght + 3);
    });

    it('invalidFormInputs() to return true when inputs are invalid', () => {

        component.news = mockNewsView;
        component.news.title = '';

        expect(component.invalidFormInputs()).toBeTruthy();
    });

    it('invalidFormInputs() to return false when inputs are valid', () => {

        component.news = mockNewsView;

        expect(component.invalidFormInputs()).toBeTrue();
    });

    it(`errorMessage() should return 'Name is required!'`, () => {
        const message = 'Name is required!';

        expect(component.errorMessage()).toEqual(message);
    });

});
