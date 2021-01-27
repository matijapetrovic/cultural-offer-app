import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { Role } from 'src/app/modules/authentication/role';
import { AddReplyComponent } from 'src/app/modules/reviews/components/add-reply/add-reply.component';
import { Review } from 'src/app/modules/reviews/review';

@Component({
  selector: 'app-offer-review-item',
  templateUrl: './offer-review-item.component.html',
  styleUrls: ['./offer-review-item.component.scss']
})
export class OfferReviewItemComponent implements OnInit {
  @Input()
  review: Review;

  @Input()
  culturalOfferId: number;

  @Output()
  replyAddedEvent: EventEmitter<void> = new EventEmitter<void>();

  userIsAdmin: boolean;



  constructor(
    private authenticationService: AuthenticationService,
    private dialogService: DialogService,
    public ref: DynamicDialogRef
  ) { }

  ngOnInit(): void {
    this.authenticationService.currentUser.subscribe((user) => this.userIsAdmin = user && user.role.includes(Role.ROLE_ADMIN));
  }

  showAddReplyDialog(): void {
    this.ref = this.dialogService.open(AddReplyComponent, {

      data: {
        culturalOfferId: this.culturalOfferId,
        reviewId: this.review.id
      },
      header: 'Add reply',
      width: '30%',
      dismissableMask: true
    });

    this.ref.onClose.subscribe((submitted: boolean) => {
      if (submitted) {
        this.replyAddedEvent.emit();
      }
    });
  }
}
