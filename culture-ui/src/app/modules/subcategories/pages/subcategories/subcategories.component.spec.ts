import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { SubcategoriesComponent } from './subcategories.component';
import { mockCategoryNames, mockSubcategoriesPage } from 'src/app/shared/testing/mock-data';
import { of } from 'rxjs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CategoriesService } from 'src/app/modules/categories/categories.service';
import { SubcategoriesService } from '../../subcategories.service';


describe('SubcategoriesComponent', () => {
    let component: SubcategoriesComponent;
    let categoriesService: CategoriesService;
    let fixture: ComponentFixture<SubcategoriesComponent>;

    beforeEach(async () => {
      const subcategoriesServiceMock = {
        getSubcategories: jasmine.createSpy('getSubcategories')
          .and.returnValue(of(mockSubcategoriesPage)),
        deleteSubcategory: jasmine.createSpy('deleteSubcategory')
          .and.returnValue(of({})),
      };

      const categoriesServiceMock = {
        getCategoryNames: jasmine.createSpy('getCategoryNames')
            .and.returnValue(of(mockCategoryNames))
      };

      const dialogRefMock = {
        close: () => { },
        onClose: jasmine.createSpy('onClose').and.returnValue({subscribe: () => { }})
      };

      const messageService = {
        add: jasmine.createSpy('add').and.returnValue({})
      };

      const confirmationServiceMock = {
        confirm: {
          accept: jasmine.createSpy('accept').and.callThrough(),
          reject: jasmine.createSpy('reject').and.callThrough(),
         },
      };

      await TestBed.configureTestingModule({
        declarations: [ SubcategoriesComponent ],
        imports: [
          FormsModule,
          ReactiveFormsModule,
          HttpClientTestingModule],
        providers: [{ provide: DynamicDialogRef, useValue: dialogRefMock },
                    { provide: ConfirmationService, useValue: confirmationServiceMock},
                    { provide: SubcategoriesService, useValue: subcategoriesServiceMock },
                    { provide: MessageService, useValue: messageService },
                    { provide: CategoriesService, useValue: categoriesServiceMock } ]
      })
      .compileComponents();
    });

    beforeEach(() => {
      fixture = TestBed.createComponent(SubcategoriesComponent);
      component = fixture.componentInstance;
      categoriesService = TestBed.inject(CategoriesService);
      fixture.detectChanges();
    });


    it('should create', fakeAsync(() => {
    expect(component).toBeTruthy();
    }));

    it('showAddForm() should add subcategory when inputs are added', fakeAsync(() => {
        component.showAddForm();
        tick();

        spyOn(component, 'getSubcategories');
        spyOn(component.ref, 'close')
            .and.returnValue(component.messageService.add({}))
            .and.callFake(() => component.getSubcategories());
        component.ref.close();
        tick();

        expect(component.ref).toBeTruthy();
        expect(component.ref.close).toHaveBeenCalled();
        expect(component.getSubcategories).toHaveBeenCalled();
        expect(component.messageService.add).toHaveBeenCalled();
      }));

    it('showUpdateForm() should get categories when inputs are added', fakeAsync(() => {
        const subcategory = {
            id: 1,
            name: 'Subcategory1',
            categoryId: 1
        };
        spyOn(component, 'getSubcategories');
        component.showUpdateForm(subcategory);
        tick();

        spyOn(component.ref, 'close')
            .and.returnValue(component.messageService.add({}))
            .and.callFake(() => component.getSubcategories());
        component.ref.close();
        tick();

        expect(component.ref).toBeTruthy();
        expect(component.ref.close).toHaveBeenCalled();
        expect(component.getSubcategories).toHaveBeenCalled();
        expect(component.messageService.add).toHaveBeenCalled();
    }));

    it('showDeleteForm() should delete category when accepted', fakeAsync(() => {
        const subcategory = {
            id: 1,
            name: 'Subcategory1',
            categoryId: 1
        };
        spyOn(component, 'getSubcategories');
        spyOn<any>(component.confirmationService, 'confirm').and.callFake((params: any) => {
            component.messageService.add({});
            params.accept();
        });
        component.showDeleteForm(subcategory);
        tick();

        expect(component.confirmationService.confirm).toBeTruthy();
        expect(component.messageService.add).toHaveBeenCalled();
        expect(component.getSubcategories).toHaveBeenCalled();
    }));

    it('showDeleteForm() reject should write rejecting a message', fakeAsync(() => {
        const id = 1;
        spyOn<any>(component.confirmationService, 'confirm').and.callFake((params: any) => {
            params.reject();
        });
        component.showDeleteForm(id);
        tick();

        expect(component.confirmationService.confirm).toBeTruthy();
    }));


    it('deleteSubcategory() should delete subcategory if given subcategory is valid', fakeAsync(() => {
        const subcategory = {
            id: 1,
            name: 'Subcategory1',
            categoryId: 1
        };
        spyOn(component, 'getSubcategories');
        component.deleteSubcategory(subcategory);

        expect(component.getSubcategories).toHaveBeenCalled();
    }));

    it('getSubategories() should get subcategories', fakeAsync(() => {
        component.subcategoriesPage = null;
        component.tempCategory = {
            id: 1,
            name: 'category1'
        };
        component.getSubcategories();

        tick();

        expect(component.subcategoriesPage).toBeTruthy();
      }));

    it('categoryChanged() should invoke getSubcategories', fakeAsync(() => {

        const category = {
            id: 1,
            name: 'category1'
        };
        spyOn(component, 'getSubcategories');
        component.categoryChanged(category);

        expect(component.getSubcategories).toHaveBeenCalled();
        expect(component.tempCategory).toEqual(category);

    }));

    it('should have categories after calling getCategories()', fakeAsync(() => {
        component.categories = [];
        fixture.detectChanges();

        component.getCategories();
        tick();

        expect(categoriesService.getCategoryNames).toHaveBeenCalled();
        expect(component.categories.length).toEqual(mockCategoryNames.length);
        expect(component.categories[0].id).toEqual(mockCategoryNames[0].id);
        expect(component.categories[0].name).toEqual(mockCategoryNames[0].name);
        expect(component.categories[1].id).toEqual(mockCategoryNames[1].id);
        expect(component.categories[1].name).toEqual(mockCategoryNames[1].name);
      }));


    it('getNextCategories() should get categories from next page', fakeAsync(() => {
        spyOn(component, 'getSubcategories');
        component.getNextSubcategories();

        expect(component.getSubcategories).toHaveBeenCalled();
      }));

    it('getPrevCategories() should get categories from previous page', fakeAsync(() => {
        spyOn(component, 'getSubcategories');
        component.getPrevSubcategories();

        expect(component.getSubcategories).toHaveBeenCalled();
      }));

});
