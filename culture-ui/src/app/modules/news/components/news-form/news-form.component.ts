import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ImageService } from 'src/app/core/services/image.service';

import { NewsToAdd, NewsView } from '../../news';

@Component({
  selector: 'app-news-form',
  templateUrl: './news-form.component.html',
  styleUrls: ['./news-form.component.scss']
})
export class NewsFormComponent implements OnInit {

    @Input()
    model: NewsView;

    @Output()
    formSubmitted = new EventEmitter<NewsToAdd>();

    @ViewChild('subcategorySelect') subcategorySelect;

    newImages: Array<File>;

    news: NewsView;

    constructor(
        private imageService: ImageService
    ) {
    }

    ngOnInit(): void {
        this.setUp();
        this.newImages = new Array<File>();
    }

    setUp(): void {
        if (this.model) {
            this.news = JSON.parse(JSON.stringify(this.model));
        }
        else {
            this.model = {
                id: null,
                title: '',
                culturalOfferId: null,
                text: '',
                images: [],
                imagesIds: []
            };
            this.news = {...this.model};
        }
    }

    appendFile(event: any): void {
        this.newImages.push(event.target.files.item(0));

        const reader = new FileReader();

        reader.onload = (image: any) => {
            this.news.images.push(image.target.result);
        };

        reader.readAsDataURL(event.target.files[0]);
    }

    removeImage(imageUrl: string): void {
        const index: number = this.news.images.indexOf(imageUrl);
        this.news.images.splice(index, 1);

        if (index < this.news.imagesIds.length) {
            this.news.imagesIds.splice(index, 1);
        }
        else {
            this.newImages.splice(index - this.news.imagesIds.length, 1);
        }
    }

    onSubmit(): void {

        if (this.newImages.length > 0) {
            this.imageService.addImages(this.imageFormData())
                .subscribe(imageIds => {
                this.updateImagesIds(imageIds);
                this.returnNews();
            });
        }
        else {
            this.returnNews();
        }
    }

    updateImagesIds(imagesIds: number[]): void {
        this.news.imagesIds = this.news.imagesIds.concat(imagesIds);
    }

    imageFormData(): FormData {
        const imageData = new FormData();

        for (let i = 0; i < this.newImages.length; i++) {
            imageData.append('images', this.newImages[i]);
        }

        return imageData;
    }


    returnNews(): any {

        const retVal: NewsToAdd = {
            id: this.news.id,
            title: this.news.title,
            text: this.news.text,
            culturalOfferId: this.news.culturalOfferId,
            images: this.news.imagesIds
        };

        this.formSubmitted.emit(retVal);
    }

    invalidFormInputs(): boolean {
        if (this.news.title === '' || this.news.title === null) {
            return true;
        }
        if (this.news.text === '' || this.news.text === null) {
            return true;
        }
        return false;
    }

    errorMessage(): string {
        return 'Name is required!';
    }

}
