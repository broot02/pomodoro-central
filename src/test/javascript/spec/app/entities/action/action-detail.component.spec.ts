/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PomodoroTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ActionDetailComponent } from '../../../../../../main/webapp/app/entities/action/action-detail.component';
import { ActionService } from '../../../../../../main/webapp/app/entities/action/action.service';
import { Action } from '../../../../../../main/webapp/app/entities/action/action.model';

describe('Component Tests', () => {

    describe('Action Management Detail Component', () => {
        let comp: ActionDetailComponent;
        let fixture: ComponentFixture<ActionDetailComponent>;
        let service: ActionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [ActionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ActionService,
                    JhiEventManager
                ]
            }).overrideTemplate(ActionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Action(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.action).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
