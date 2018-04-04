import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AssociatedUser } from './associated-user.model';
import { AssociatedUserService } from './associated-user.service';

@Component({
    selector: 'jhi-associated-user-detail',
    templateUrl: './associated-user-detail.component.html'
})
export class AssociatedUserDetailComponent implements OnInit, OnDestroy {

    associatedUser: AssociatedUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private associatedUserService: AssociatedUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAssociatedUsers();
    }

    load(id) {
        this.associatedUserService.find(id)
            .subscribe((associatedUserResponse: HttpResponse<AssociatedUser>) => {
                this.associatedUser = associatedUserResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAssociatedUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'associatedUserListModification',
            (response) => this.load(this.associatedUser.id)
        );
    }
}
