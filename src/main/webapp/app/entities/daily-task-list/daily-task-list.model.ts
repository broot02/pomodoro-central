import { BaseEntity } from './../../shared';

export class DailyTaskList implements BaseEntity {
    constructor(
        public id?: number,
        public taskDate?: any,
        public tasks?: BaseEntity[],
    ) {
    }
}
