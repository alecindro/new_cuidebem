import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReferences } from 'app/shared/model/references.model';

type EntityResponseType = HttpResponse<IReferences>;
type EntityArrayResponseType = HttpResponse<IReferences[]>;

@Injectable({ providedIn: 'root' })
export class ReferencesService {
  public resourceUrl = SERVER_API_URL + 'api/references';

  constructor(protected http: HttpClient) {}

  create(references: IReferences): Observable<EntityResponseType> {
    return this.http.post<IReferences>(this.resourceUrl, references, { observe: 'response' });
  }

  update(references: IReferences): Observable<EntityResponseType> {
    return this.http.put<IReferences>(this.resourceUrl, references, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReferences>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReferences[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
