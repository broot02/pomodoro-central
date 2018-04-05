import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DailyTaskList } from './daily-task-list.model';
import { DailyTaskListService } from './daily-task-list.service';

@Injectable()
export class DailyTaskListPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dailyTaskListService: DailyTaskListService

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
                this.dailyTaskListService.find(id)
                    .subscribe((dailyTaskListResponse: HttpResponse<DailyTaskList>) => {
                        const dailyTaskList: DailyTaskList = dailyTaskListResponse.body;
                        if (dailyTaskList.taskDate) {
                            dailyTaskList.taskDate = {
                                year: dailyTaskList.taskDate.getFullYear(),
                                month: dailyTaskList.taskDate.getMonth() + 1,
                                day: dailyTaskList.taskDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.dailyTaskListModalRef(component, dailyTaskList);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dailyTaskListModalRef(component, new DailyTaskList());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dailyTaskListModalRef(component: Component, dailyTaskList: DailyTaskList): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dailyTaskList = dailyTaskList;
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
