/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PomodoroTestModule } from '../../../test.module';
import { UserPokeComponent } from '../../../../../../main/webapp/app/entities/user-poke/user-poke.component';
import { UserPokeService } from '../../../../../../main/webapp/app/entities/user-poke/user-poke.service';
import { UserPoke } from '../../../../../../main/webapp/app/entities/user-poke/user-poke.model';

describe('Component Tests', () => {

    describe('UserPoke Management Component', () => {
        let comp: UserPokeComponent;
        let fixture: ComponentFixture<UserPokeComponent>;
        let service: UserPokeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [UserPokeComponent],
                providers: [
                    UserPokeService
                ]
            })
            .overrideTemplate(UserPokeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPokeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPokeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserPoke(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userPokes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
