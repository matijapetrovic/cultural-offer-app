import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'primeng/api';
import { AddReplyComponent } from './add-reply/add-reply.component';



@NgModule({
  declarations: [AddReplyComponent],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    AddReplyComponent
  ]
})
export class RepliesModule { }
