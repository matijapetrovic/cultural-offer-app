import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, fakeAsync, TestBed } from "@angular/core/testing";
import { DynamicDialogRef } from "primeng/dynamicdialog";
import { mockCulturalOfferToAdd, mockNewsToAdd } from "src/app/shared/testing/mock-data";
import { NewsService } from "../../news.service";
import { AddNewsComponent } from "./add-news.component";


describe('AddOfferComponent', () => {

    let component: AddNewsComponent;
    let fixture: ComponentFixture<AddNewsComponent>;

    beforeEach(async () => {

        const newsServiceMock = {
            addNews : jasmine.createSpy('addNews')
              .and.returnValue({
                subscribe: () => {
                    component.loading = false;
                    component.ref.close(true);
                }
              })
          };

          const dialogRefMock = {
            close: () => { },
            onClose: jasmine.createSpy('onClose').and.returnValue({subscribe: () => { true; }})
          };

          await TestBed.configureTestingModule({
            declarations: [AddNewsComponent],
            imports: [HttpClientTestingModule],
            providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock},
                        { provide: NewsService, useValue: newsServiceMock }]
          })
          .compileComponents();

    })

    beforeEach(() => {
        fixture = TestBed.createComponent(AddNewsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

      it('should create', () => {
        expect(component).toBeTruthy();
      });

      it('postNews() should post offer when form is submited', fakeAsync(() => {

        spyOn(component.ref, 'close');

        component.postNews(mockNewsToAdd);

        expect(component.loading).toBeFalsy();
        expect(component.ref.close).toHaveBeenCalled();
      }));

});