import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Task } from './task.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Task>;

@Injectable()
export class TaskService {

    private resourceUrl =  SERVER_API_URL + 'api/tasks';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(task: Task): Observable<EntityResponseType> {
        const copy = this.convert(task);
        return this.http.post<Task>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(task: Task): Observable<EntityResponseType> {
        const copy = this.convert(task);
        return this.http.put<Task>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Task>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Task[]>> {
        const options = createRequestOption(req);
        return this.http.get<Task[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Task[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Task = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Task[]>): HttpResponse<Task[]> {
        const jsonResponse: Task[] = res.body;
        const body: Task[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Task.
     */
    private convertItemFromServer(task: Task): Task {
        const copy: Task = Object.assign({}, task);
        copy.statusDate = this.dateUtils
            .convertLocalDateFromServer(task.statusDate);
        return copy;
    }

    /**
     * Convert a Task to a JSON which can be sent to the server.
     */
    private convert(task: Task): Task {
        const copy: Task = Object.assign({}, task);
        copy.statusDate = this.dateUtils
            .convertLocalDateToServer(task.statusDate);
        return copy;
    }
}
