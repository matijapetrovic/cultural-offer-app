import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { MessageService } from './services/message.service';
import { HttpErrorHandler } from './services/http-error-handler.service';

import {MenubarModule} from 'primeng/menubar';


@NgModule({
    declarations: [HeaderComponent],
    imports: [
        RouterModule,
        CommonModule,
        MenubarModule
    ],
    providers: [
        HttpErrorHandler,
        MessageService,
    ],
    exports: [HeaderComponent],
})

export class CoreModule {}