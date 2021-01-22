import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ReviewsService } from 'src/app/modules/reviews/reviews.service';
import { DynamicDialogRef } from 'primeng/dynamicdialog';

import { FileUploadModule } from 'primeng/fileupload';
import { HttpClientModule } from '@angular/common/http';
import { ReviewToAdd } from 'src/app/modules/reviews/review';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-review-for-offer',
  templateUrl: './add-review-for-offer.component.html',
  styleUrls: ['./add-review-for-offer.component.scss'],
  providers: [FileUploadModule, HttpClientModule]
})
export class AddReviewForOfferComponent implements OnInit {
  private culturalOfferId: number;
  private reviewToAdd: ReviewToAdd;
  addReviewForm: FormGroup;

  constructor(
    private reviewsService: ReviewsService,
    private formBuilder: FormBuilder,
    private ref: DynamicDialogRef,
    private fileUploadModule: FileUploadModule,
    private httpClientModule: HttpClientModule,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.culturalOfferId = +this.route.snapshot.params.id;
    this.addReviewForm = this.formBuilder.group({
      comment: ['', Validators.required],
      rating: [2, Validators.required],
      images: [new Array<string>()]
    });
  }

  onSubmit(): void {
    this.reviewToAdd = this.addReviewForm.value;
    this.reviewsService
      .addReview(this.reviewToAdd, this.culturalOfferId)
      .subscribe(() => {
        this.addReviewForm.reset();

        const submitted = true;
        this.ref.close(submitted);
      });
  }
}
