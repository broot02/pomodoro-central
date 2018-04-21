import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserPoke } from './user-poke.model';
import { UserPokeService } from './user-poke.service';

@Component({
    selector: 'jhi-user-poke-detail',
    templateUrl: './user-poke-detail.component.html'
})
export class UserPokeDetailComponent implements OnInit, OnDestroy {

    userPoke: UserPoke;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userPokeService: UserPokeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserPokes();
    }

    load(id) {
        this.userPokeService.find(id)
            .subscribe((userPokeResponse: HttpResponse<UserPoke>) => {
                this.userPoke = userPokeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserPokes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userPokeListModification',
            (response) => this.load(this.userPoke.id)
        );
    }
}
