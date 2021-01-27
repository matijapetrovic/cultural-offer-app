import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { of } from 'rxjs';
import { RepliesService } from '../../replies.service';
import { ReviewsService } from '../../reviews.service';

import { AddReplyComponent } from './add-reply.component';

describe('AddReplyComponent', () => {
  let component: AddReplyComponent;
  let fixture: ComponentFixture<AddReplyComponent>;
  let repliesService: RepliesService;
  let ref: DynamicDialogRef;
  let messageService: MessageService;


  const offerId = 1;
  const reviewID = 1;

  beforeEach(async () => {
    const repliesServiceMock = {
      addReply: jasmine.createSpy('addreply')
        .and.returnValue(of())
    };

    const dialogConfigMock = jasmine.createSpyObj('DynamicDialogConfig', [], { data: { culturalOfferId: offerId, reviewId: reviewID } });

    const refMock = {
      close: jasmine.createSpy('close')
        .and.callFake(() => { })
    };

    const messageServiceMock = {
      add: jasmine.createSpy('add').and.callThrough()
    };

    await TestBed.configureTestingModule({
      declarations: [AddReplyComponent],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
      ],
      providers: [
        { provide: RepliesService, useValue: repliesServiceMock },
        { provide: DynamicDialogRef, useValue: refMock },
        { provide: MessageService, useValue: messageServiceMock },
        { provide: DynamicDialogConfig, useValue: dialogConfigMock }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddReplyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();


    repliesService = TestBed.inject(RepliesService);
    ref = TestBed.inject(DynamicDialogRef);
    messageService = TestBed.inject(MessageService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize addReplyForm', () => {
    component.ngOnInit();
    expect(component.addReplyForm).toBeDefined();
  });

  it('form should be invalid when empty', () => {
    expect(component.addReplyForm.valid).toBeFalsy();
  });

  it('onSubmit() form with valid inputs should be submitted', () => {
    component.addReplyForm.controls.comment.setValue('Some comment');

    expect(component.addReplyForm.valid).toBeTruthy();

    component.onSubmit();

    expect(repliesService.addReply).toHaveBeenCalledTimes(1);
  });

  it('onSubmit() form with invalid inputs should not be submitted', () => {
    component.addReplyForm.controls.comment.setValue('');

    expect(component.addReplyForm.invalid).toBeTruthy();

    component.onSubmit();

    expect(repliesService.addReply).toHaveBeenCalledTimes(0);
  });

  it('should show success message', () => {
    component.showSuccessMessage();
    expect(messageService.add).toHaveBeenCalled();
  });

  it('should get cultural offer id', () => {
    expect(component.offerId).toEqual(offerId);
  });

  it('should get review id', () => {
    expect(component.reviewID).toEqual(reviewID);
  });
});
