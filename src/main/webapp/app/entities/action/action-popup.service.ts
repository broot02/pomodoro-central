import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Action } from './action.model';
import { ActionService } from './action.service';

@Injectable()
export class ActionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private actionService: ActionService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.actionService.find(id)
                    .subscribe((actionResponse: HttpResponse<Action>) => {
                        const action: Action = actionResponse.body;
                        action.startTime = this.datePipe
                            .transform(action.startTime, 'yyyy-MM-ddTHH:mm:ss');
                        action.endTime = this.datePipe
                            .transform(action.endTime, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.actionModalRef(component, action);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.actionModalRef(component, new Action());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    actionModalRef(component: Component, action: Action): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.action = action;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
