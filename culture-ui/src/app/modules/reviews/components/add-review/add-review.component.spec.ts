import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { of } from 'rxjs';
import { ImageService } from 'src/app/core/services/image.service';
import { ReviewsService } from '../../reviews.service';

import { AddReviewComponent } from './add-review.component';
import { RatingModule } from 'primeng/rating';
import { mockImage1, mockImage2, mockImagesToAdd } from 'src/app/shared/testing/mock-data';


describe('AddReviewComponent', () => {
  let component: AddReviewComponent;
  let fixture: ComponentFixture<AddReviewComponent>;
  let imageService: ImageService;
  let reviewsService: ReviewsService;

  beforeEach(async () => {
    const imageServiceMock = {
      addImages: jasmine.createSpy('addImages')
        .and.returnValue(of({ body: [1, 2] }))
    };

    const reviewsServiceMock = {
      addReview: jasmine.createSpy('addReview')
        .and.returnValue(of())
    };

    const dialogConfigMock = jasmine.createSpyObj('DynamicDialogConfig', [], { data: { culturalOfferId: 1 } });

    // const dialogConfigMock: DynamicDialogConfig = {
    //   data: 1
    // };

    const dialogRefMock = {
      close: () => { }
    };

    await TestBed.configureTestingModule({
      declarations: [AddReviewComponent],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        RatingModule  // because of formControlName is in p-rating
      ],
      providers: [
        { provide: DynamicDialogRef, useValue: dialogRefMock },
        { provide: ReviewsService, useValue: reviewsServiceMock },
        { provide: ImageService, useValue: imageServiceMock },
        { provide: DynamicDialogConfig, useValue: dialogConfigMock }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();


    imageService = TestBed.inject(ImageService);
    reviewsService = TestBed.inject(ReviewsService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create addReviewForm', () => {
    expect(component.addReviewForm).toBeDefined();
  });

  it('form should be invalid when empty', () => {
    expect(component.addReviewForm.valid).toBeFalsy();
  });

  it('addToImagesToArray() should change number of imagesToAdd', () => {
    expect(component.imagesToAdd.getAll('images').length).toEqual(0);

    const customEvent = {
      currentFiles: [
        mockImage1,
        mockImage2
      ]
    };

    component.addToImagesToArray(customEvent);

    expect(component.imagesToAdd.getAll('images').length).toEqual(2);
  });

  it('onSubmit() form with valid inputs should be submitted', () => {

    component.addReviewForm.controls.rating.setValue(5);
    component.addReviewForm.controls.comment.setValue('Some comment');
    component.imagesToAdd = mockImagesToAdd;

    expect(component.addReviewForm.valid).toBeTruthy();


    component.onSubmit();

    expect(imageService.addImages).toHaveBeenCalledTimes(1);
    expect(reviewsService.addReview).toHaveBeenCalledTimes(1);
  });

  it('onSubmit() form with empty comment should not be submitted', () => {
    component.addReviewForm.controls.rating.setValue(5);
    component.addReviewForm.controls.comment.setValue('');
    component.imagesToAdd = mockImagesToAdd;


    expect(component.addReviewForm.invalid).toBeTruthy();

    component.onSubmit();

    expect(imageService.addImages).toHaveBeenCalledTimes(0);
    expect(reviewsService.addReview).toHaveBeenCalledTimes(0);
  });

  it('should show process message', () => {
    component.showProccessMessage();
    expect(component.messageService.add).toHaveBeenCalled();
  });

  it('should show success message', () => {
    component.showProccessMessage();
    expect(component.messageService.add).toHaveBeenCalled();
  });

  it('should get cultural offer id', () => {
    expect(component.offerId).toEqual(1);
  });
});
