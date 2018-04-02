import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ActionComponent } from './action.component';
import { ActionDetailComponent } from './action-detail.component';
import { ActionPopupComponent } from './action-dialog.component';
import { ActionDeletePopupComponent } from './action-delete-dialog.component';

export const actionRoute: Routes = [
    {
        path: 'action',
        component: ActionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Actions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'action/:id',
        component: ActionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Actions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actionPopupRoute: Routes = [
    {
        path: 'action-new',
        component: ActionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Actions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'action/:id/edit',
        component: ActionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Actions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'action/:id/delete',
        component: ActionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Actions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
