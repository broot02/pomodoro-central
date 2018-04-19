import { BaseEntity } from './../../shared';

export const enum Status {
    'New',
    'Progress',
    'Completed',
    'Interrupted'
}

export class Action implements BaseEntity {
    constructor(
        public id?: number,
        public status?: Status,
        public duration?: number,
        public startTime?: any,
        public endTime?: any,
        public taskId?: number,
    ) {
    }
}
