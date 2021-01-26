import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { NewsView } from '../../news';
import { NewsService } from '../../news.service';

@Component({
  selector: 'app-update-news',
  templateUrl: './update-news.component.html',
  styleUrls: ['./update-news.component.scss']
})
export class UpdateNewsComponent implements OnInit {

    //Offer which we update
    news: NewsView;
    culturalOfferId: number;

    loading:boolean;

    constructor(
        private config: DynamicDialogConfig,
        private newsService: NewsService,
        public ref: DynamicDialogRef
    ) { 
        //deep copying original object
        this.culturalOfferId = config.data.culturalofferId;
        this.news = {...this.config.data.news};
        this.loading = false;
    }

  ngOnInit(): void {
  }

  updateNews(news:any) {

      this.loading = true;

      this.newsService.updateNews(this.culturalOfferId, news)
      .subscribe(() => {
          this.loading = false;
          this.ref.close(true);
      });
  }

}
