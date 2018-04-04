/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PomodoroTestModule } from '../../../test.module';
import { AssociatedUserDetailComponent } from '../../../../../../main/webapp/app/entities/associated-user/associated-user-detail.component';
import { AssociatedUserService } from '../../../../../../main/webapp/app/entities/associated-user/associated-user.service';
import { AssociatedUser } from '../../../../../../main/webapp/app/entities/associated-user/associated-user.model';

describe('Component Tests', () => {

    describe('AssociatedUser Management Detail Component', () => {
        let comp: AssociatedUserDetailComponent;
        let fixture: ComponentFixture<AssociatedUserDetailComponent>;
        let service: AssociatedUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [AssociatedUserDetailComponent],
                providers: [
                    AssociatedUserService
                ]
            })
            .overrideTemplate(AssociatedUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssociatedUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssociatedUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AssociatedUser(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.associatedUser).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
