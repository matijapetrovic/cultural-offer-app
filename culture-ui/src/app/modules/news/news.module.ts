import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewsPageComponent } from './pages/news-page/news-page.component';
import { NewsRoutingModule } from './news-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from 'src/app/core/core.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { AddNewsComponent } from './pages/add-news/add-news.component';
import { UpdateNewsComponent } from './pages/update-news/update-news.component';
import { SharedModule } from 'src/app/shared/shared.module';
@NgModule({
  declarations: [NewsPageComponent, AddNewsComponent, UpdateNewsComponent],
  imports: [
    CommonModule,
    NewsRoutingModule,
    SharedModule,
    FlexLayoutModule,
    CoreModule,
    ReactiveFormsModule
  ]
})
export class NewsModule { }
