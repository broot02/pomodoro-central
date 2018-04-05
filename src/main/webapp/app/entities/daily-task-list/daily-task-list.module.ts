import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PomodoroSharedModule } from '../../shared';
import {
    DailyTaskListService,
    DailyTaskListPopupService,
    DailyTaskListComponent,
    DailyTaskListDetailComponent,
    DailyTaskListDialogComponent,
    DailyTaskListPopupComponent,
    DailyTaskListDeletePopupComponent,
    DailyTaskListDeleteDialogComponent,
    dailyTaskListRoute,
    dailyTaskListPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dailyTaskListRoute,
    ...dailyTaskListPopupRoute,
];

@NgModule({
    imports: [
        PomodoroSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DailyTaskListComponent,
        DailyTaskListDetailComponent,
        DailyTaskListDialogComponent,
        DailyTaskListDeleteDialogComponent,
        DailyTaskListPopupComponent,
        DailyTaskListDeletePopupComponent,
    ],
    entryComponents: [
        DailyTaskListComponent,
        DailyTaskListDialogComponent,
        DailyTaskListPopupComponent,
        DailyTaskListDeleteDialogComponent,
        DailyTaskListDeletePopupComponent,
    ],
    providers: [
        DailyTaskListService,
        DailyTaskListPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PomodoroDailyTaskListModule {}
