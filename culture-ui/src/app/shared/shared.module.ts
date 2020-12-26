import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CommonModule } from '@angular/common';
import { PaginationBarComponent } from './components/pagination-bar/pagination-bar.component';


import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { LocalDatePipe } from './pipes/local-date.pipe';

import {ButtonModule} from 'primeng/button';
import {CarouselModule} from 'primeng/carousel';
import {ConfirmPopupModule} from 'primeng/confirmpopup';
import {ConfirmationService} from 'primeng/api';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {CardModule} from 'primeng/card';
import { SliceTextPipe } from './pipes/slice-text.pipe';
import {GMapModule} from 'primeng/gmap';
import {RatingModule} from 'primeng/rating';
import { RoundPipe } from './pipes/round.pipe';


@NgModule({
    declarations: [
    PaginationBarComponent,
    LocalDatePipe,
    SliceTextPipe,
    RoundPipe],
    imports: [
        CommonModule,
        FormsModule,
        FlexLayoutModule,
        MatButtonModule,
        ButtonModule,
        CarouselModule,
        ConfirmPopupModule,
        ScrollPanelModule,
        CardModule,
        GMapModule,
        RatingModule
    ],
    exports: [
        PaginationBarComponent,
        FormsModule,
        LocalDatePipe,
        SliceTextPipe,
        RoundPipe,
        FlexLayoutModule,
        MatButtonModule,
        ButtonModule,
        CarouselModule,
        ConfirmPopupModule,
        ScrollPanelModule,
        CardModule,
        GMapModule,
        RatingModule
    ],
    providers: [
        ConfirmationService
    ]
})
export class SharedModule { }
