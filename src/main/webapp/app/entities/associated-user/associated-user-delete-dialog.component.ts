import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AssociatedUser } from './associated-user.model';
import { AssociatedUserPopupService } from './associated-user-popup.service';
import { AssociatedUserService } from './associated-user.service';

@Component({
    selector: 'jhi-associated-user-delete-dialog',
    templateUrl: './associated-user-delete-dialog.component.html'
})
export class AssociatedUserDeleteDialogComponent {

    associatedUser: AssociatedUser;

    constructor(
        private associatedUserService: AssociatedUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.associatedUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'associatedUserListModification',
                content: 'Deleted an associatedUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-associated-user-delete-popup',
    template: ''
})
export class AssociatedUserDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private associatedUserPopupService: AssociatedUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.associatedUserPopupService
                .open(AssociatedUserDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
