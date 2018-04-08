import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DailyTaskList } from './daily-task-list.model';
import { DailyTaskListPopupService } from './daily-task-list-popup.service';
import { DailyTaskListService } from './daily-task-list.service';

@Component({
    selector: 'jhi-daily-task-list-delete-dialog',
    templateUrl: './daily-task-list-delete-dialog.component.html'
})
export class DailyTaskListDeleteDialogComponent {

    dailyTaskList: DailyTaskList;

    constructor(
        private dailyTaskListService: DailyTaskListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dailyTaskListService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dailyTaskListListModification',
                content: 'Deleted an dailyTaskList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-daily-task-list-delete-popup',
    template: ''
})
export class DailyTaskListDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dailyTaskListPopupService: DailyTaskListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dailyTaskListPopupService
                .open(DailyTaskListDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
