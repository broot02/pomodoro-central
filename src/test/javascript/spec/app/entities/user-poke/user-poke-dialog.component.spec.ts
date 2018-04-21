/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PomodoroTestModule } from '../../../test.module';
import { UserPokeDialogComponent } from '../../../../../../main/webapp/app/entities/user-poke/user-poke-dialog.component';
import { UserPokeService } from '../../../../../../main/webapp/app/entities/user-poke/user-poke.service';
import { UserPoke } from '../../../../../../main/webapp/app/entities/user-poke/user-poke.model';
import { AssociatedUserService } from '../../../../../../main/webapp/app/entities/associated-user';

describe('Component Tests', () => {

    describe('UserPoke Management Dialog Component', () => {
        let comp: UserPokeDialogComponent;
        let fixture: ComponentFixture<UserPokeDialogComponent>;
        let service: UserPokeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PomodoroTestModule],
                declarations: [UserPokeDialogComponent],
                providers: [
                    AssociatedUserService,
                    UserPokeService
                ]
            })
            .overrideTemplate(UserPokeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPokeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPokeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserPoke(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userPoke = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userPokeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserPoke();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userPoke = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userPokeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
