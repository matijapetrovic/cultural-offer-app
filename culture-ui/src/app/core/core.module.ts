import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { MessageService } from './services/message.service';
import { HttpErrorHandler } from './services/http-error-handler.service';

@NgModule({
    declarations: [HeaderComponent],
    imports: [
        RouterModule,
        CommonModule
    ],
    providers: [
        HttpErrorHandler,
        MessageService,
    ],
    exports: [],
})

export class CoreModule {}