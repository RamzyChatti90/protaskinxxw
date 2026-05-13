import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';
import { SprintService } from '../service/sprint.service';
import { ISprint } from '../sprint.model';
import { SprintFormService } from './sprint-form.service';

import { SprintUpdateComponent } from './sprint-update.component';

describe('Sprint Management Update Component', () => {
  let comp: SprintUpdateComponent;
  let fixture: ComponentFixture<SprintUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sprintFormService: SprintFormService;
  let sprintService: SprintService;
  let appUserService: AppUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SprintUpdateComponent],
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
      .overrideTemplate(SprintUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SprintUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sprintFormService = TestBed.inject(SprintFormService);
    sprintService = TestBed.inject(SprintService);
    appUserService = TestBed.inject(AppUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call AppUser query and add missing value', () => {
      const sprint: ISprint = { id: 28936 };
      const owner: IAppUser = { id: 14418 };
      sprint.owner = owner;

      const appUserCollection: IAppUser[] = [{ id: 14418 }];
      jest.spyOn(appUserService, 'query').mockReturnValue(of(new HttpResponse({ body: appUserCollection })));
      const additionalAppUsers = [owner];
      const expectedCollection: IAppUser[] = [...additionalAppUsers, ...appUserCollection];
      jest.spyOn(appUserService, 'addAppUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sprint });
      comp.ngOnInit();

      expect(appUserService.query).toHaveBeenCalled();
      expect(appUserService.addAppUserToCollectionIfMissing).toHaveBeenCalledWith(
        appUserCollection,
        ...additionalAppUsers.map(expect.objectContaining),
      );
      expect(comp.appUsersSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const sprint: ISprint = { id: 28936 };
      const owner: IAppUser = { id: 14418 };
      sprint.owner = owner;

      activatedRoute.data = of({ sprint });
      comp.ngOnInit();

      expect(comp.appUsersSharedCollection).toContainEqual(owner);
      expect(comp.sprint).toEqual(sprint);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISprint>>();
      const sprint = { id: 19154 };
      jest.spyOn(sprintFormService, 'getSprint').mockReturnValue(sprint);
      jest.spyOn(sprintService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sprint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sprint }));
      saveSubject.complete();

      // THEN
      expect(sprintFormService.getSprint).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sprintService.update).toHaveBeenCalledWith(expect.objectContaining(sprint));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISprint>>();
      const sprint = { id: 19154 };
      jest.spyOn(sprintFormService, 'getSprint').mockReturnValue({ id: null });
      jest.spyOn(sprintService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sprint: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sprint }));
      saveSubject.complete();

      // THEN
      expect(sprintFormService.getSprint).toHaveBeenCalled();
      expect(sprintService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISprint>>();
      const sprint = { id: 19154 };
      jest.spyOn(sprintService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sprint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sprintService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
