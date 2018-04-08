import { BaseEntity } from './../../shared';

export const enum Status {
    'New',
    'Progress',
    'Completed',
    'Cancel'
}

export class Task implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public status?: Status,
        public statusDate?: any,
        public estimate?: number,
        public usernameLogin?: string,
        public usernameId?: number,
        public actions?: BaseEntity[],
        public dailyTaskLists?: BaseEntity[],
    ) {
    }
}
