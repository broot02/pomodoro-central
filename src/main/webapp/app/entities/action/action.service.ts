import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Action } from './action.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Action>;

@Injectable()
export class ActionService {

    private resourceUrl =  SERVER_API_URL + 'api/actions';

    constructor(private http: HttpClient) { }

    create(action: Action): Observable<EntityResponseType> {
        const copy = this.convert(action);
        return this.http.post<Action>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(action: Action): Observable<EntityResponseType> {
        const copy = this.convert(action);
        return this.http.put<Action>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Action>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Action[]>> {
        const options = createRequestOption(req);
        return this.http.get<Action[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Action[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Action = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Action[]>): HttpResponse<Action[]> {
        const jsonResponse: Action[] = res.body;
        const body: Action[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Action.
     */
    private convertItemFromServer(action: Action): Action {
        const copy: Action = Object.assign({}, action);
        return copy;
    }

    /**
     * Convert a Action to a JSON which can be sent to the server.
     */
    private convert(action: Action): Action {
        const copy: Action = Object.assign({}, action);
        return copy;
    }
}
