/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PomodoroTestModule } from '../../../test.module';
import { DailyTaskListDialogComponent } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list-dialog.component';
import { DailyTaskListService } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list.service';
import { DailyTaskList } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list.model';
import { TaskService } from '../../../../../../main/webapp/app/entities/task';

describe('Component Tests', () => {

    describe('DailyTaskList Management Dialog Component', () => {
        let comp: DailyTaskListDialogComponent;
        let fixture: ComponentFixture<DailyTaskListDialogComponent>;
        let service: DailyTaskListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [DailyTaskListDialogComponent],
                providers: [
                    TaskService,
                    DailyTaskListService
                ]
            })
            .overrideTemplate(DailyTaskListDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DailyTaskListDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DailyTaskListService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DailyTaskList(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dailyTaskList = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dailyTaskListListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DailyTaskList();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dailyTaskList = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dailyTaskListListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
