import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';
import { ITask } from '../task.model';
import { TaskService } from '../service/task.service';
import { TaskFormService } from './task-form.service';

import { TaskUpdateComponent } from './task-update.component';

describe('Task Management Update Component', () => {
  let comp: TaskUpdateComponent;
  let fixture: ComponentFixture<TaskUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let taskFormService: TaskFormService;
  let taskService: TaskService;
  let categoryService: CategoryService;
  let appUserService: AppUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TaskUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TaskUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TaskUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    taskFormService = TestBed.inject(TaskFormService);
    taskService = TestBed.inject(TaskService);
    categoryService = TestBed.inject(CategoryService);
    appUserService = TestBed.inject(AppUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Category query and add missing value', () => {
      const task: ITask = { id: 22244 };
      const category: ICategory = { id: 6752 };
      task.category = category;

      const categoryCollection: ICategory[] = [{ id: 6752 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ task });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategories.map(expect.objectContaining),
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('should call AppUser query and add missing value', () => {
      const task: ITask = { id: 22244 };
      const assignedTo: IAppUser = { id: 14418 };
      task.assignedTo = assignedTo;
      const createdBy: IAppUser = { id: 14418 };
      task.createdBy = createdBy;

      const appUserCollection: IAppUser[] = [{ id: 14418 }];
      jest.spyOn(appUserService, 'query').mockReturnValue(of(new HttpResponse({ body: appUserCollection })));
      const additionalAppUsers = [assignedTo, createdBy];
      const expectedCollection: IAppUser[] = [...additionalAppUsers, ...appUserCollection];
      jest.spyOn(appUserService, 'addAppUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ task });
      comp.ngOnInit();

      expect(appUserService.query).toHaveBeenCalled();
      expect(appUserService.addAppUserToCollectionIfMissing).toHaveBeenCalledWith(
        appUserCollection,
        ...additionalAppUsers.map(expect.objectContaining),
      );
      expect(comp.appUsersSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const task: ITask = { id: 22244 };
      const category: ICategory = { id: 6752 };
      task.category = category;
      const assignedTo: IAppUser = { id: 14418 };
      task.assignedTo = assignedTo;
      const createdBy: IAppUser = { id: 14418 };
      task.createdBy = createdBy;

      activatedRoute.data = of({ task });
      comp.ngOnInit();

      expect(comp.categoriesSharedCollection).toContainEqual(category);
      expect(comp.appUsersSharedCollection).toContainEqual(assignedTo);
      expect(comp.appUsersSharedCollection).toContainEqual(createdBy);
      expect(comp.task).toEqual(task);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITask>>();
      const task = { id: 25192 };
      jest.spyOn(taskFormService, 'getTask').mockReturnValue(task);
      jest.spyOn(taskService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ task });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: task }));
      saveSubject.complete();

      // THEN
      expect(taskFormService.getTask).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(taskService.update).toHaveBeenCalledWith(expect.objectContaining(task));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITask>>();
      const task = { id: 25192 };
      jest.spyOn(taskFormService, 'getTask').mockReturnValue({ id: null });
      jest.spyOn(taskService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ task: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: task }));
      saveSubject.complete();

      // THEN
      expect(taskFormService.getTask).toHaveBeenCalled();
      expect(taskService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITask>>();
      const task = { id: 25192 };
      jest.spyOn(taskService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ task });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(taskService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategory', () => {
      it('should forward to categoryService', () => {
        const entity = { id: 6752 };
        const entity2 = { id: 4374 };
        jest.spyOn(categoryService, 'compareCategory');
        comp.compareCategory(entity, entity2);
        expect(categoryService.compareCategory).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAppUser', () => {
      it('should forward to appUserService', () => {
        const entity = { id: 14418 };
        const entity2 = { id: 16679 };
        jest.spyOn(appUserService, 'compareAppUser');
        comp.compareAppUser(entity, entity2);
        expect(appUserService.compareAppUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
