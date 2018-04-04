import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PomodoroSharedModule } from '../../shared';
import { PomodoroAdminModule } from '../../admin/admin.module';
import {
    TaskService,
    TaskPopupService,
    TaskComponent,
    TaskDetailComponent,
    TaskDialogComponent,
    TaskPopupComponent,
    TaskDeletePopupComponent,
    TaskDeleteDialogComponent,
    taskRoute,
    taskPopupRoute,
} from './';

const ENTITY_STATES = [
    ...taskRoute,
    ...taskPopupRoute,
];

@NgModule({
    imports: [
        PomodoroSharedModule,
        PomodoroAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TaskComponent,
        TaskDetailComponent,
        TaskDialogComponent,
        TaskDeleteDialogComponent,
        TaskPopupComponent,
        TaskDeletePopupComponent,
    ],
    entryComponents: [
        TaskComponent,
        TaskDialogComponent,
        TaskPopupComponent,
        TaskDeleteDialogComponent,
        TaskDeletePopupComponent,
    ],
    providers: [
        TaskService,
        TaskPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PomodoroTaskModule {}
