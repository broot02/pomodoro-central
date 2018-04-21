import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserPokeComponent } from './user-poke.component';
import { UserPokeDetailComponent } from './user-poke-detail.component';
import { UserPokePopupComponent } from './user-poke-dialog.component';
import { UserPokeDeletePopupComponent } from './user-poke-delete-dialog.component';

export const userPokeRoute: Routes = [
    {
        path: 'user-poke',
        component: UserPokeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPokes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-poke/:id',
        component: UserPokeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPokes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userPokePopupRoute: Routes = [
    {
        path: 'user-poke-new',
        component: UserPokePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPokes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-poke/:id/edit',
        component: UserPokePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPokes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-poke/:id/delete',
        component: UserPokeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPokes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
