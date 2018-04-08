import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DailyTaskListComponent } from './daily-task-list.component';
import { DailyTaskListDetailComponent } from './daily-task-list-detail.component';
import { DailyTaskListPopupComponent } from './daily-task-list-dialog.component';
import { DailyTaskListDeletePopupComponent } from './daily-task-list-delete-dialog.component';

export const dailyTaskListRoute: Routes = [
    {
        path: 'daily-task-list',
        component: DailyTaskListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DailyTaskLists'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'daily-task-list/:id',
        component: DailyTaskListDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DailyTaskLists'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dailyTaskListPopupRoute: Routes = [
    {
        path: 'daily-task-list-new',
        component: DailyTaskListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DailyTaskLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'daily-task-list/:id/edit',
        component: DailyTaskListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DailyTaskLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'daily-task-list/:id/delete',
        component: DailyTaskListDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DailyTaskLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
