/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PomodoroTestModule } from '../../../test.module';
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
                    ActionService
                ]
            })
            .overrideTemplate(ActionDetailComponent, '')
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

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Action(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.action).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
