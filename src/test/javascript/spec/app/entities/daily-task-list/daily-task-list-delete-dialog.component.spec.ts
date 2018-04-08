/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PomodoroTestModule } from '../../../test.module';
import { DailyTaskListDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list-delete-dialog.component';
import { DailyTaskListService } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list.service';

describe('Component Tests', () => {

    describe('DailyTaskList Management Delete Component', () => {
        let comp: DailyTaskListDeleteDialogComponent;
        let fixture: ComponentFixture<DailyTaskListDeleteDialogComponent>;
        let service: DailyTaskListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [DailyTaskListDeleteDialogComponent],
                providers: [
                    DailyTaskListService
                ]
            })
            .overrideTemplate(DailyTaskListDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DailyTaskListDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DailyTaskListService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
