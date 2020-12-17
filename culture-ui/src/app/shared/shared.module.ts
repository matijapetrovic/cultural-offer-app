import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaginationBarComponent } from './components/pagination-bar/pagination-bar.component';


import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { StarRatingModule } from 'angular-star-rating';

@NgModule({
    declarations: [
    PaginationBarComponent],
    imports: [
        CommonModule,
        FlexLayoutModule,
        MatButtonModule,
        StarRatingModule.forRoot()
    ],
    exports: [
        PaginationBarComponent,
        FlexLayoutModule,
        MatButtonModule,
        StarRatingModule
    ]
})
export class SharedModule { }
