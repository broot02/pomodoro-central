/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PomodoroTestModule } from '../../../test.module';
import { TaskComponent } from '../../../../../../main/webapp/app/entities/task/task.component';
import { TaskService } from '../../../../../../main/webapp/app/entities/task/task.service';
import { Task } from '../../../../../../main/webapp/app/entities/task/task.model';

describe('Component Tests', () => {

    describe('Task Management Component', () => {
        let comp: TaskComponent;
        let fixture: ComponentFixture<TaskComponent>;
        let service: TaskService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [TaskComponent],
                providers: [
                    TaskService
                ]
            })
            .overrideTemplate(TaskComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaskComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaskService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Task(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tasks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
