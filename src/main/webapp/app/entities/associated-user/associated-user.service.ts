import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AssociatedUser } from './associated-user.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AssociatedUser>;

@Injectable()
export class AssociatedUserService {

    private resourceUrl =  SERVER_API_URL + 'api/associated-users';

    constructor(private http: HttpClient) { }

    create(associatedUser: AssociatedUser): Observable<EntityResponseType> {
        const copy = this.convert(associatedUser);
        return this.http.post<AssociatedUser>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(associatedUser: AssociatedUser): Observable<EntityResponseType> {
        const copy = this.convert(associatedUser);
        return this.http.put<AssociatedUser>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AssociatedUser>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AssociatedUser[]>> {
        const options = createRequestOption(req);
        return this.http.get<AssociatedUser[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AssociatedUser[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AssociatedUser = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AssociatedUser[]>): HttpResponse<AssociatedUser[]> {
        const jsonResponse: AssociatedUser[] = res.body;
        const body: AssociatedUser[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AssociatedUser.
     */
    private convertItemFromServer(associatedUser: AssociatedUser): AssociatedUser {
        const copy: AssociatedUser = Object.assign({}, associatedUser);
        return copy;
    }

    /**
     * Convert a AssociatedUser to a JSON which can be sent to the server.
     */
    private convert(associatedUser: AssociatedUser): AssociatedUser {
        const copy: AssociatedUser = Object.assign({}, associatedUser);
        return copy;
    }
}
