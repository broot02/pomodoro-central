import { BaseEntity } from './../../shared';

export class UserPoke implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public associatedUserId?: number,
    ) {
    }
}
