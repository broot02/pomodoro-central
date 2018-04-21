import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserPoke } from './user-poke.model';
import { UserPokePopupService } from './user-poke-popup.service';
import { UserPokeService } from './user-poke.service';

@Component({
    selector: 'jhi-user-poke-delete-dialog',
    templateUrl: './user-poke-delete-dialog.component.html'
})
export class UserPokeDeleteDialogComponent {

    userPoke: UserPoke;

    constructor(
        private userPokeService: UserPokeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userPokeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userPokeListModification',
                content: 'Deleted an userPoke'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-poke-delete-popup',
    template: ''
})
export class UserPokeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userPokePopupService: UserPokePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userPokePopupService
                .open(UserPokeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
