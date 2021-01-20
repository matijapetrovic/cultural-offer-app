import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { HttpErrorHandler } from './services/http-error-handler.service';

import {MenubarModule} from 'primeng/menubar';
import { SharedModule } from '../shared/shared.module';
import { AuthGuard } from './guards/auth.guard';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ErrorInterceptor } from './interceptors/error.interceptor';


@NgModule({
    declarations: [HeaderComponent],
    imports: [
        RouterModule,
        CommonModule,
        MenubarModule,
        SharedModule
    ],
    providers: [
        HttpErrorHandler,
        AuthGuard,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: JwtInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorInterceptor,
            multi: true
        }
    ],
    exports: [HeaderComponent],
})

export class CoreModule {}