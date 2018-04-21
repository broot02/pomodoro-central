import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserPoke } from './user-poke.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserPoke>;

@Injectable()
export class UserPokeService {

    private resourceUrl =  SERVER_API_URL + 'api/user-pokes';

    constructor(private http: HttpClient) { }

    create(userPoke: UserPoke): Observable<EntityResponseType> {
        const copy = this.convert(userPoke);
        return this.http.post<UserPoke>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userPoke: UserPoke): Observable<EntityResponseType> {
        const copy = this.convert(userPoke);
        return this.http.put<UserPoke>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserPoke>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserPoke[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserPoke[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserPoke[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserPoke = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserPoke[]>): HttpResponse<UserPoke[]> {
        const jsonResponse: UserPoke[] = res.body;
        const body: UserPoke[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserPoke.
     */
    private convertItemFromServer(userPoke: UserPoke): UserPoke {
        const copy: UserPoke = Object.assign({}, userPoke);
        return copy;
    }

    /**
     * Convert a UserPoke to a JSON which can be sent to the server.
     */
    private convert(userPoke: UserPoke): UserPoke {
        const copy: UserPoke = Object.assign({}, userPoke);
        return copy;
    }
}
