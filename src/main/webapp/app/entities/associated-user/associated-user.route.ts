import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AssociatedUserComponent } from './associated-user.component';
import { AssociatedUserDetailComponent } from './associated-user-detail.component';
import { AssociatedUserPopupComponent } from './associated-user-dialog.component';
import { AssociatedUserDeletePopupComponent } from './associated-user-delete-dialog.component';

export const associatedUserRoute: Routes = [
    {
        path: 'associated-user',
        component: AssociatedUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedUsers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'associated-user/:id',
        component: AssociatedUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const associatedUserPopupRoute: Routes = [
    {
        path: 'associated-user-new',
        component: AssociatedUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'associated-user/:id/edit',
        component: AssociatedUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'associated-user/:id/delete',
        component: AssociatedUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
