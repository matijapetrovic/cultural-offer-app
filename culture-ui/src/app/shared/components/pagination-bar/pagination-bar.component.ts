import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-pagination-bar',
  templateUrl: './pagination-bar.component.html',
  styleUrls: ['./pagination-bar.component.scss']
})
export class PaginationBarComponent implements OnInit {
  @Input()
  hasNext: boolean;

  @Input()
  hasPrev: boolean;

  @Input()
  currentPage: number;

  @Output()
  navigatePrevEvent = new EventEmitter<void>();

  @Output()
  navigateNextEvent = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  navigatePrevious() {
    this.navigatePrevEvent.emit();
  }

  navigateNext() {
    this.navigateNextEvent.emit();
  }

}
