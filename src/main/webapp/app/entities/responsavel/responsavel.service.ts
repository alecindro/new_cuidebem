import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResponsavel } from 'app/shared/model/responsavel.model';

type EntityResponseType = HttpResponse<IResponsavel>;
type EntityArrayResponseType = HttpResponse<IResponsavel[]>;

@Injectable({ providedIn: 'root' })
export class ResponsavelService {
  public resourceUrl = SERVER_API_URL + 'api/responsavels';

  constructor(protected http: HttpClient) {}

  create(responsavel: IResponsavel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavel);
    return this.http
      .post<IResponsavel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(responsavel: IResponsavel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavel);
    return this.http
      .put<IResponsavel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResponsavel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResponsavel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(responsavel: IResponsavel): IResponsavel {
    const copy: IResponsavel = Object.assign({}, responsavel, {
      dataNascimento:
        responsavel.dataNascimento != null && responsavel.dataNascimento.isValid() ? responsavel.dataNascimento.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataNascimento = res.body.dataNascimento != null ? moment(res.body.dataNascimento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((responsavel: IResponsavel) => {
        responsavel.dataNascimento = responsavel.dataNascimento != null ? moment(responsavel.dataNascimento) : null;
      });
    }
    return res;
  }
}
