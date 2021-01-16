import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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
import {DropdownModule} from 'primeng/dropdown';
import { RoundPipe } from './pipes/round.pipe';
import { TableModule } from 'primeng/table';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import {InputTextModule} from 'primeng/inputtext';

@NgModule({
    declarations: [
    PaginationBarComponent,
    LocalDatePipe,
    SliceTextPipe,
    RoundPipe],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        FlexLayoutModule,
        MatButtonModule,
        ButtonModule,
        CarouselModule,
        ConfirmPopupModule,
        ScrollPanelModule,
        CardModule,
        GMapModule,
        RatingModule,
        DropdownModule,
        TableModule,
        DynamicDialogModule,
        ConfirmDialogModule,
        InputTextModule
    ],
    exports: [
        PaginationBarComponent,
        FormsModule,
        ReactiveFormsModule,
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
        RatingModule,
        DropdownModule,
        TableModule,
        DynamicDialogModule,
        ConfirmDialogModule,
        InputTextModule
    ],
    providers: [
        ConfirmationService,
    ]
})
export class SharedModule { }
