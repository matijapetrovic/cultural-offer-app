import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddReviewComponent } from './components/add-review/add-review.component';
import { SharedModule } from './../../shared/shared.module';
import { FileUploadModule } from 'primeng/fileupload';
import { AddReplyComponent } from './components/add-reply/add-reply.component';

@NgModule({
  declarations: [AddReviewComponent, AddReplyComponent],
  imports: [
    CommonModule,
    SharedModule,
    FileUploadModule
  ],
  exports: [AddReviewComponent, AddReplyComponent]
})
export class ReviewsModule { }
