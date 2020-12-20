import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaginationBarComponent } from './components/pagination-bar/pagination-bar.component';


import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { StarRatingModule } from 'angular-star-rating';
import { LocalDatePipe } from './pipes/local-date.pipe';

@NgModule({
    declarations: [
    PaginationBarComponent,
    LocalDatePipe],
    imports: [
        CommonModule,
        FlexLayoutModule,
        MatButtonModule,
        StarRatingModule.forRoot()
    ],
    exports: [
        PaginationBarComponent,
        LocalDatePipe,
        FlexLayoutModule,
        MatButtonModule,
        StarRatingModule
    ]
})
export class SharedModule { }
