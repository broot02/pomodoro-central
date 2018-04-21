/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PomodoroTestModule } from '../../../test.module';
import { UserPokeDetailComponent } from '../../../../../../main/webapp/app/entities/user-poke/user-poke-detail.component';
import { UserPokeService } from '../../../../../../main/webapp/app/entities/user-poke/user-poke.service';
import { UserPoke } from '../../../../../../main/webapp/app/entities/user-poke/user-poke.model';

describe('Component Tests', () => {

    describe('UserPoke Management Detail Component', () => {
        let comp: UserPokeDetailComponent;
        let fixture: ComponentFixture<UserPokeDetailComponent>;
        let service: UserPokeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [UserPokeDetailComponent],
                providers: [
                    UserPokeService
                ]
            })
            .overrideTemplate(UserPokeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPokeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPokeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserPoke(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userPoke).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
