import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PomodoroSharedModule } from '../../shared';
import {
    UserPokeService,
    UserPokePopupService,
    UserPokeComponent,
    UserPokeDetailComponent,
    UserPokeDialogComponent,
    UserPokePopupComponent,
    UserPokeDeletePopupComponent,
    UserPokeDeleteDialogComponent,
    userPokeRoute,
    userPokePopupRoute,
} from './';

const ENTITY_STATES = [
    ...userPokeRoute,
    ...userPokePopupRoute,
];

@NgModule({
    imports: [
        PomodoroSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserPokeComponent,
        UserPokeDetailComponent,
        UserPokeDialogComponent,
        UserPokeDeleteDialogComponent,
        UserPokePopupComponent,
        UserPokeDeletePopupComponent,
    ],
    entryComponents: [
        UserPokeComponent,
        UserPokeDialogComponent,
        UserPokePopupComponent,
        UserPokeDeleteDialogComponent,
        UserPokeDeletePopupComponent,
    ],
    providers: [
        UserPokeService,
        UserPokePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PomodoroUserPokeModule {}
