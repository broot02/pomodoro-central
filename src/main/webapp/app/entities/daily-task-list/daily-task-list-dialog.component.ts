import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DailyTaskList } from './daily-task-list.model';
import { DailyTaskListPopupService } from './daily-task-list-popup.service';
import { DailyTaskListService } from './daily-task-list.service';
import { Task, TaskService } from '../task';

@Component({
    selector: 'jhi-daily-task-list-dialog',
    templateUrl: './daily-task-list-dialog.component.html'
})
export class DailyTaskListDialogComponent implements OnInit {

    dailyTaskList: DailyTaskList;
    isSaving: boolean;

    tasks: Task[];
    taskDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dailyTaskListService: DailyTaskListService,
        private taskService: TaskService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.taskService.query()
            .subscribe((res: HttpResponse<Task[]>) => { this.tasks = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dailyTaskList.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dailyTaskListService.update(this.dailyTaskList));
        } else {
            this.subscribeToSaveResponse(
                this.dailyTaskListService.create(this.dailyTaskList));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DailyTaskList>>) {
        result.subscribe((res: HttpResponse<DailyTaskList>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DailyTaskList) {
        this.eventManager.broadcast({ name: 'dailyTaskListListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTaskById(index: number, item: Task) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-daily-task-list-popup',
    template: ''
})
export class DailyTaskListPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dailyTaskListPopupService: DailyTaskListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dailyTaskListPopupService
                    .open(DailyTaskListDialogComponent as Component, params['id']);
            } else {
                this.dailyTaskListPopupService
                    .open(DailyTaskListDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
