/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PomodoroTestModule } from '../../../test.module';
import { AssociatedUserComponent } from '../../../../../../main/webapp/app/entities/associated-user/associated-user.component';
import { AssociatedUserService } from '../../../../../../main/webapp/app/entities/associated-user/associated-user.service';
import { AssociatedUser } from '../../../../../../main/webapp/app/entities/associated-user/associated-user.model';

describe('Component Tests', () => {

    describe('AssociatedUser Management Component', () => {
        let comp: AssociatedUserComponent;
        let fixture: ComponentFixture<AssociatedUserComponent>;
        let service: AssociatedUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [AssociatedUserComponent],
                providers: [
                    AssociatedUserService
                ]
            })
            .overrideTemplate(AssociatedUserComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssociatedUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssociatedUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AssociatedUser(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.associatedUsers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
