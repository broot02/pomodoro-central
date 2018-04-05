import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DailyTaskList } from './daily-task-list.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DailyTaskList>;

@Injectable()
export class DailyTaskListService {

    private resourceUrl =  SERVER_API_URL + 'api/daily-task-lists';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(dailyTaskList: DailyTaskList): Observable<EntityResponseType> {
        const copy = this.convert(dailyTaskList);
        return this.http.post<DailyTaskList>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dailyTaskList: DailyTaskList): Observable<EntityResponseType> {
        const copy = this.convert(dailyTaskList);
        return this.http.put<DailyTaskList>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DailyTaskList>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DailyTaskList[]>> {
        const options = createRequestOption(req);
        return this.http.get<DailyTaskList[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DailyTaskList[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DailyTaskList = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DailyTaskList[]>): HttpResponse<DailyTaskList[]> {
        const jsonResponse: DailyTaskList[] = res.body;
        const body: DailyTaskList[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DailyTaskList.
     */
    private convertItemFromServer(dailyTaskList: DailyTaskList): DailyTaskList {
        const copy: DailyTaskList = Object.assign({}, dailyTaskList);
        copy.taskDate = this.dateUtils
            .convertLocalDateFromServer(dailyTaskList.taskDate);
        return copy;
    }

    /**
     * Convert a DailyTaskList to a JSON which can be sent to the server.
     */
    private convert(dailyTaskList: DailyTaskList): DailyTaskList {
        const copy: DailyTaskList = Object.assign({}, dailyTaskList);
        copy.taskDate = this.dateUtils
            .convertLocalDateToServer(dailyTaskList.taskDate);
        return copy;
    }
}
