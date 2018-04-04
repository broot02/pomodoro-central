import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PomodoroSharedModule } from '../../shared';
import { PomodoroAdminModule } from '../../admin/admin.module';
import {
    AssociatedUserService,
    AssociatedUserPopupService,
    AssociatedUserComponent,
    AssociatedUserDetailComponent,
    AssociatedUserDialogComponent,
    AssociatedUserPopupComponent,
    AssociatedUserDeletePopupComponent,
    AssociatedUserDeleteDialogComponent,
    associatedUserRoute,
    associatedUserPopupRoute,
} from './';

const ENTITY_STATES = [
    ...associatedUserRoute,
    ...associatedUserPopupRoute,
];

@NgModule({
    imports: [
        PomodoroSharedModule,
        PomodoroAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AssociatedUserComponent,
        AssociatedUserDetailComponent,
        AssociatedUserDialogComponent,
        AssociatedUserDeleteDialogComponent,
        AssociatedUserPopupComponent,
        AssociatedUserDeletePopupComponent,
    ],
    entryComponents: [
        AssociatedUserComponent,
        AssociatedUserDialogComponent,
        AssociatedUserPopupComponent,
        AssociatedUserDeleteDialogComponent,
        AssociatedUserDeletePopupComponent,
    ],
    providers: [
        AssociatedUserService,
        AssociatedUserPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PomodoroAssociatedUserModule {}
