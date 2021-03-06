import { Component, OnInit, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ReviewsService } from 'src/app/modules/reviews/reviews.service';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

import { ReviewToAdd } from 'src/app/modules/reviews/review';
import { MessageService } from 'primeng/api';
import { ImageService } from 'src/app/core/services/image.service';

@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.scss']
})
export class AddReviewComponent implements OnInit {
  private culturalOfferId: number;
  private reviewToAdd: ReviewToAdd;
  public addReviewForm: FormGroup;

  imagesToAdd: FormData;

  public reviewAddedEvent: EventEmitter<void> = new EventEmitter<void>();

  constructor(
    private reviewsService: ReviewsService,
    private formBuilder: FormBuilder,
    private ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private messageService: MessageService,
    private imageService: ImageService
  ) {
    this.culturalOfferId = this.config.data.culturalOfferId;
  }

  ngOnInit(): void {
    this.imagesToAdd = new FormData();

    this.addReviewForm = this.formBuilder.group({
      comment: ['', Validators.required],
      rating: [2, Validators.required],
      images: [new Array<number>()]
    });
  }

  addToImagesToArray(event: any): void {

    this.imagesToAdd = new FormData();

    for (const image of event.currentFiles) {
      this.imagesToAdd.append('images', image);
    }

  }

  onSubmit(): void {
    if (this.addReviewForm.invalid) {
      return;
    }

    this.reviewToAdd = this.addReviewForm.value;

    this.ref.close(this.reviewAddedEvent);
    this.showProccessMessage();

    this.imageService
      .addImages(this.imagesToAdd)
      .subscribe(
        (imagesId: number[]) => {
          this.reviewToAdd.images = imagesId;

          this.reviewsService
            .addReview(this.reviewToAdd, this.culturalOfferId)
            .subscribe(
              () => {
                this.addReviewForm.reset();

                this.showSuccessMessage();

                this.reviewAddedEvent.emit();
              });
        });
  }

  showProccessMessage(): void {
    this.messageService.add({
      severity: 'info',
      summary: 'Your request is beeing processed.',
      detail: 'Uploading review can take couple of minutes depending of size of images.'
    });
  }

  showSuccessMessage(): void {
    this.messageService.add({
      severity: 'success',
      summary: 'Success!',
      detail: 'Your review was added.'
    });
  }

  get offerId(): number {
    return this.culturalOfferId;
  }
}
