import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Action } from './action.model';
import { ActionService } from './action.service';

@Component({
    selector: 'jhi-action-detail',
    templateUrl: './action-detail.component.html'
})
export class ActionDetailComponent implements OnInit, OnDestroy {

    action: Action;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private actionService: ActionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActions();
    }

    load(id) {
        this.actionService.find(id).subscribe((action) => {
            this.action = action;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'actionListModification',
            (response) => this.load(this.action.id)
        );
    }
}
