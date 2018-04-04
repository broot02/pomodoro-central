/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PomodoroTestModule } from '../../../test.module';
import { ActionComponent } from '../../../../../../main/webapp/app/entities/action/action.component';
import { ActionService } from '../../../../../../main/webapp/app/entities/action/action.service';
import { Action } from '../../../../../../main/webapp/app/entities/action/action.model';

describe('Component Tests', () => {

    describe('Action Management Component', () => {
        let comp: ActionComponent;
        let fixture: ComponentFixture<ActionComponent>;
        let service: ActionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [ActionComponent],
                providers: [
                    ActionService
                ]
            })
            .overrideTemplate(ActionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Action(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.actions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
