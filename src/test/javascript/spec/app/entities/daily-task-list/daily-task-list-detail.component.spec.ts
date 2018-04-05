/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PomodoroTestModule } from '../../../test.module';
import { DailyTaskListDetailComponent } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list-detail.component';
import { DailyTaskListService } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list.service';
import { DailyTaskList } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list.model';

describe('Component Tests', () => {

    describe('DailyTaskList Management Detail Component', () => {
        let comp: DailyTaskListDetailComponent;
        let fixture: ComponentFixture<DailyTaskListDetailComponent>;
        let service: DailyTaskListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [DailyTaskListDetailComponent],
                providers: [
                    DailyTaskListService
                ]
            })
            .overrideTemplate(DailyTaskListDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DailyTaskListDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DailyTaskListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DailyTaskList(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dailyTaskList).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
