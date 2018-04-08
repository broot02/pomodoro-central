import { BaseEntity } from './../../shared';

export class AssociatedUser implements BaseEntity {
    constructor(
        public id?: number,
        public userLogin?: string,
        public userId?: number,
        public associatedUserLogin?: string,
        public associatedUserId?: number,
    ) {
    }
}
