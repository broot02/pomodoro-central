/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PomodoroTestModule } from '../../../test.module';
import { DailyTaskListComponent } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list.component';
import { DailyTaskListService } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list.service';
import { DailyTaskList } from '../../../../../../main/webapp/app/entities/daily-task-list/daily-task-list.model';

describe('Component Tests', () => {

    describe('DailyTaskList Management Component', () => {
        let comp: DailyTaskListComponent;
        let fixture: ComponentFixture<DailyTaskListComponent>;
        let service: DailyTaskListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [DailyTaskListComponent],
                providers: [
                    DailyTaskListService
                ]
            })
            .overrideTemplate(DailyTaskListComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DailyTaskListComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DailyTaskListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DailyTaskList(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dailyTaskLists[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
