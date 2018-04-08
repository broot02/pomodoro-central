import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Action } from './action.model';
import { ActionPopupService } from './action-popup.service';
import { ActionService } from './action.service';
import { Task, TaskService } from '../task';

@Component({
    selector: 'jhi-action-dialog',
    templateUrl: './action-dialog.component.html'
})
export class ActionDialogComponent implements OnInit {

    action: Action;
    isSaving: boolean;

    tasks: Task[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private actionService: ActionService,
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
        if (this.action.id !== undefined) {
            this.subscribeToSaveResponse(
                this.actionService.update(this.action));
        } else {
            this.subscribeToSaveResponse(
                this.actionService.create(this.action));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Action>>) {
        result.subscribe((res: HttpResponse<Action>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Action) {
        this.eventManager.broadcast({ name: 'actionListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-action-popup',
    template: ''
})
export class ActionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actionPopupService: ActionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.actionPopupService
                    .open(ActionDialogComponent as Component, params['id']);
            } else {
                this.actionPopupService
                    .open(ActionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
