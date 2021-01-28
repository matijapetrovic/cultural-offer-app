import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { mockCulturalOfferToAdd, mockCulturalOfferView, mockNewsToAdd } from 'src/app/shared/testing/mock-data';
import { NewsService } from '../../news.service';
import { UpdateNewsComponent } from './update-news.component';


describe('UpdateOfferComponent', () => {

    let component: UpdateNewsComponent;
    let fixture: ComponentFixture<UpdateNewsComponent>;

    beforeEach(async () => {

        const newsServiceMock = {
            updateNews : jasmine.createSpy('updateNews')
              .and.returnValue({
                subscribe: () => {
                    component.loading = false;
                    component.ref.close(true);
                }
              })
          };

        const dialogRefMock = {
            close: () => { },
            onClose: jasmine.createSpy('onClose').and.returnValue({subscribe: () => { }})
          };

        const dialogRefConfigMock = {
            data: {
              news: mockNewsToAdd
            }
          };

        await TestBed.configureTestingModule({
            declarations: [UpdateNewsComponent],
            imports: [HttpClientTestingModule],
            providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock},
                        { provide: NewsService, useValue: newsServiceMock },
                        { provide: DynamicDialogConfig, useValue: dialogRefConfigMock }]
          })
          .compileComponents();

    });

    beforeEach(() => {
        fixture = TestBed.createComponent(UpdateNewsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

    it('should create', () => {
        expect(component).toBeTruthy();
      });

    it('updateNews() should put offer when form is submited', fakeAsync(() => {

        spyOn(component.ref, 'close');

        component.updateNews(mockNewsToAdd);

        expect(component.loading).toBeFalsy();
        expect(component.ref.close).toHaveBeenCalled();
      }));

});
