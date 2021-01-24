import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { RepliesService } from '../../replies.service';
import { ReplyToAdd } from '../../review';

@Component({
  selector: 'app-add-reply',
  templateUrl: './add-reply.component.html',
  styleUrls: ['./add-reply.component.scss']
})
export class AddReplyComponent implements OnInit {
  addReplyForm: FormGroup;

  private culturalOfferId: number;
  private reviewId: number;

  constructor(
    private formBuilder: FormBuilder,
    private repliesService: RepliesService,
    private config: DynamicDialogConfig,
    private ref: DynamicDialogRef,
    private messageService: MessageService
  ) {
    this.culturalOfferId = this.config.data.culturalOfferId;
    this.reviewId = this.config.data.reviewId;
  }

  ngOnInit(): void {
    this.addReplyForm = this.formBuilder.group({
      comment: ['', Validators.required]
    });
  }

  onSubmit(): void {
    const replyToAdd: ReplyToAdd = this.addReplyForm.value;

    this.repliesService.addReply(this.culturalOfferId, this.reviewId, replyToAdd)
      .subscribe(() => {
        const submitted = true;
        this.ref.close(submitted);
        this.showSuccessMessage();
      });
    this.addReplyForm.reset();
  }

  showSuccessMessage(): void {
    this.messageService.add({
      severity: 'success',
      summary: 'Reply successfully added.',
      detail: 'Reply successfully added.'
    });
    setTimeout(() => this.messageService.clear(), 5000);
  }
}
