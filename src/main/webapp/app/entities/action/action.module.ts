import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PomodoroSharedModule } from '../../shared';
import {
    ActionService,
    ActionPopupService,
    ActionComponent,
    ActionDetailComponent,
    ActionDialogComponent,
    ActionPopupComponent,
    ActionDeletePopupComponent,
    ActionDeleteDialogComponent,
    actionRoute,
    actionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...actionRoute,
    ...actionPopupRoute,
];

@NgModule({
    imports: [
        PomodoroSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ActionComponent,
        ActionDetailComponent,
        ActionDialogComponent,
        ActionDeleteDialogComponent,
        ActionPopupComponent,
        ActionDeletePopupComponent,
    ],
    entryComponents: [
        ActionComponent,
        ActionDialogComponent,
        ActionPopupComponent,
        ActionDeleteDialogComponent,
        ActionDeletePopupComponent,
    ],
    providers: [
        ActionService,
        ActionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PomodoroActionModule {}
