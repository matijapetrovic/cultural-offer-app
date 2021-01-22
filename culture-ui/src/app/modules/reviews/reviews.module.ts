import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddReviewComponent } from './components/add-review/add-review/add-review.component';
import { SharedModule } from './../../shared/shared.module';
import { FileUploadModule } from 'primeng/fileupload';

@NgModule({
  declarations: [AddReviewComponent],
  imports: [
    CommonModule,
    SharedModule,
    FileUploadModule
  ]
})
export class ReviewsModule { }
