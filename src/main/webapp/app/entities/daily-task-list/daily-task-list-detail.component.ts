import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DailyTaskList } from './daily-task-list.model';
import { DailyTaskListService } from './daily-task-list.service';

@Component({
    selector: 'jhi-daily-task-list-detail',
    templateUrl: './daily-task-list-detail.component.html'
})
export class DailyTaskListDetailComponent implements OnInit, OnDestroy {

    dailyTaskList: DailyTaskList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dailyTaskListService: DailyTaskListService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDailyTaskLists();
    }

    load(id) {
        this.dailyTaskListService.find(id)
            .subscribe((dailyTaskListResponse: HttpResponse<DailyTaskList>) => {
                this.dailyTaskList = dailyTaskListResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDailyTaskLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dailyTaskListListModification',
            (response) => this.load(this.dailyTaskList.id)
        );
    }
}
