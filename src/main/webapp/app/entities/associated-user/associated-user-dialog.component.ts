import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AssociatedUser } from './associated-user.model';
import { AssociatedUserPopupService } from './associated-user-popup.service';
import { AssociatedUserService } from './associated-user.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-associated-user-dialog',
    templateUrl: './associated-user-dialog.component.html'
})
export class AssociatedUserDialogComponent implements OnInit {

    associatedUser: AssociatedUser;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private associatedUserService: AssociatedUserService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.associatedUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.associatedUserService.update(this.associatedUser));
        } else {
            this.subscribeToSaveResponse(
                this.associatedUserService.create(this.associatedUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AssociatedUser>>) {
        result.subscribe((res: HttpResponse<AssociatedUser>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AssociatedUser) {
        this.eventManager.broadcast({ name: 'associatedUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-associated-user-popup',
    template: ''
})
export class AssociatedUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private associatedUserPopupService: AssociatedUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.associatedUserPopupService
                    .open(AssociatedUserDialogComponent as Component, params['id']);
            } else {
                this.associatedUserPopupService
                    .open(AssociatedUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
