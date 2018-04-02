import { BaseEntity } from './../../shared';

const enum Status {
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
        public taskId?: number,
    ) {
    }
}
