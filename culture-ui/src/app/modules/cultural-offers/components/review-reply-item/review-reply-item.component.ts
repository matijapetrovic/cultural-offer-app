import { Component, Input, OnInit } from '@angular/core';
import { Reply } from 'src/app/modules/reviews/review';

@Component({
  selector: 'app-review-reply-item',
  templateUrl: './review-reply-item.component.html',
  styleUrls: ['./review-reply-item.component.scss']
})
export class ReviewReplyItemComponent implements OnInit {
  @Input() reply: Reply;

  constructor() { }

  ngOnInit(): void {
  }

}
