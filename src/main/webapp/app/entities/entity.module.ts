import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PomodoroTaskModule } from './task/task.module';
import { PomodoroActionModule } from './action/action.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PomodoroTaskModule,
        PomodoroActionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PomodoroEntityModule {}
