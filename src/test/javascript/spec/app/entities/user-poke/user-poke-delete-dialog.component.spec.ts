/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PomodoroTestModule } from '../../../test.module';
import { UserPokeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/user-poke/user-poke-delete-dialog.component';
import { UserPokeService } from '../../../../../../main/webapp/app/entities/user-poke/user-poke.service';

describe('Component Tests', () => {

    describe('UserPoke Management Delete Component', () => {
        let comp: UserPokeDeleteDialogComponent;
        let fixture: ComponentFixture<UserPokeDeleteDialogComponent>;
        let service: UserPokeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [UserPokeDeleteDialogComponent],
                providers: [
                    UserPokeService
                ]
            })
            .overrideTemplate(UserPokeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPokeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPokeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
