import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { NewsService } from '../../news.service';

@Component({
  selector: 'app-add-news',
  templateUrl: './add-news.component.html',
  styleUrls: ['./add-news.component.scss']
})
export class AddNewsComponent implements OnInit {

  loading: boolean;
  culturalOfferId: number;

  constructor(
      config: DynamicDialogConfig,
      private newsService: NewsService,
      public ref: DynamicDialogRef
  ) { 
      this.loading = false;
      this.culturalOfferId = config.data.culturalOfferId;
  }

  ngOnInit(): void {
  }

  postNews(news:any): void {
      this.loading = true;

      this.newsService.addNews(this.culturalOfferId, news)
      .subscribe(() => {
          this.loading = false;
          this.ref.close(true);
      });
  }
}
