import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaginationBarComponent } from './components/pagination-bar/pagination-bar.component';


import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { StarRatingModule } from 'angular-star-rating';
import { LocalDatePipe } from './pipes/local-date.pipe';

import {ButtonModule} from 'primeng/button';
import {CarouselModule} from 'primeng/carousel';
import {ConfirmPopupModule} from 'primeng/confirmpopup';
import {ConfirmationService} from 'primeng/api';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {CardModule} from 'primeng/card';


@NgModule({
    declarations: [
    PaginationBarComponent,
    LocalDatePipe],
    imports: [
        CommonModule,
        FlexLayoutModule,
        MatButtonModule,
        ButtonModule,
        CarouselModule,
        ConfirmPopupModule,
        ScrollPanelModule,
        CardModule,
        StarRatingModule.forRoot()
    ],
    exports: [
        PaginationBarComponent,
        LocalDatePipe,
        FlexLayoutModule,
        MatButtonModule,
        ButtonModule,
        CarouselModule,
        ConfirmPopupModule,
        ScrollPanelModule,
        CardModule,
        StarRatingModule
    ],
    providers: [
        ConfirmationService
    ]
})
export class SharedModule { }
