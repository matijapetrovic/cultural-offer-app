import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { NewsPage } from '../../news';
import { NewsService } from '../../news.service';
import { AddNewsComponent } from '../add-news/add-news.component';
import { UpdateNewsComponent } from '../update-news/update-news.component';

@Component({
  selector: 'app-news-page',
  templateUrl: './news-page.component.html',
  styleUrls: ['./news-page.component.scss'],
  providers: [DialogService]
})
export class NewsPageComponent implements OnInit {
  culturalOfferId: number;
  newsPage: NewsPage;
  
  private page: number;
  private limit = 5;
  public ref: DynamicDialogRef;

  constructor(
    private newsService: NewsService,
    private route: ActivatedRoute,
    public dialogService: DialogService,
    public messageService: MessageService,
    public confirmationService: ConfirmationService
  ) {
    this.page = 0;
  }

  ngOnInit(): void {
    this.culturalOfferId = +this.route.snapshot.params.offerId;
    this.getNews();
  }

  showAddForm(): void {
    this.ref = this.dialogService.open(AddNewsComponent, {
      header: 'Add news',
      width: '30%',
      dismissableMask: true
    });

    this.ref.onClose.subscribe((submitted) => {
      if (submitted) {
        this.getNews();
        this.messageService.add({
          severity: 'success', summary: 'News adding successful', detail: 'You have successfully added news' });
        setTimeout(() => this.messageService.clear(), 2000);
      }
    });
  }

  showUpdateForm(news: any): void {
    this.ref = this.dialogService.open(UpdateNewsComponent, {
      data: {
        news
      },
      header: 'Update news',
      width: '30%',
      dismissableMask: true
    });

    this.ref.onClose.subscribe((submitted) => {
      if (submitted) {
        this.getNews();
        this.messageService.add({ severity: 'success', summary: 'News updating successful', detail: 'You have successfully updated news' });
        setTimeout(() => this.messageService.clear(), 2000);
      }
    });
  }

  showDeleteForm(id: number): void {
    this.confirmationService.confirm({
      message: 'Do you want to delete this news?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.deleteNews(id);
        this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'You have successfuly deleted news!' });
      },
      reject: () => {
      }
    });
  }

  getNews(): void {
    this.newsService.getNews(this.culturalOfferId, this.page, this.limit).subscribe(news => this.newsPage = news);
  }

  deleteNews(id: number): void {
    // this.newsService.deleteNews(id)
    // .pipe()
    // .subscribe(
    //   () => {
    //     this.getNews();
    //   });
  }

  getNextNews(): void {
    this.page++;
    this.getNews();
  }

  getPrevNews(): void {
    this.page--;
    this.getNews();
  }

}
