import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserPoke } from './user-poke.model';
import { UserPokePopupService } from './user-poke-popup.service';
import { UserPokeService } from './user-poke.service';
import { AssociatedUser, AssociatedUserService } from '../associated-user';

@Component({
    selector: 'jhi-user-poke-dialog',
    templateUrl: './user-poke-dialog.component.html'
})
export class UserPokeDialogComponent implements OnInit {

    userPoke: UserPoke;
    isSaving: boolean;

    associatedusers: AssociatedUser[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userPokeService: UserPokeService,
        private associatedUserService: AssociatedUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.associatedUserService
            .query({filter: 'userpoke-is-null'})
            .subscribe((res: HttpResponse<AssociatedUser[]>) => {
                if (!this.userPoke.associatedUserId) {
                    this.associatedusers = res.body;
                } else {
                    this.associatedUserService
                        .find(this.userPoke.associatedUserId)
                        .subscribe((subRes: HttpResponse<AssociatedUser>) => {
                            this.associatedusers = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userPoke.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userPokeService.update(this.userPoke));
        } else {
            this.subscribeToSaveResponse(
                this.userPokeService.create(this.userPoke));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserPoke>>) {
        result.subscribe((res: HttpResponse<UserPoke>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserPoke) {
        this.eventManager.broadcast({ name: 'userPokeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAssociatedUserById(index: number, item: AssociatedUser) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-poke-popup',
    template: ''
})
export class UserPokePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userPokePopupService: UserPokePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userPokePopupService
                    .open(UserPokeDialogComponent as Component, params['id']);
            } else {
                this.userPokePopupService
                    .open(UserPokeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
