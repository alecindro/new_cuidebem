import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { References } from 'app/shared/model/references.model';
import { ReferencesService } from './references.service';
import { ReferencesComponent } from './references.component';
import { ReferencesDetailComponent } from './references-detail.component';
import { ReferencesUpdateComponent } from './references-update.component';
import { ReferencesDeletePopupComponent } from './references-delete-dialog.component';
import { IReferences } from 'app/shared/model/references.model';

@Injectable({ providedIn: 'root' })
export class ReferencesResolve implements Resolve<IReferences> {
  constructor(private service: ReferencesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReferences> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<References>) => response.ok),
        map((references: HttpResponse<References>) => references.body)
      );
    }
    return of(new References());
  }
}

export const referencesRoute: Routes = [
  {
    path: '',
    component: ReferencesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'cdbemApp.references.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReferencesDetailComponent,
    resolve: {
      references: ReferencesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cdbemApp.references.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReferencesUpdateComponent,
    resolve: {
      references: ReferencesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cdbemApp.references.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReferencesUpdateComponent,
    resolve: {
      references: ReferencesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cdbemApp.references.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const referencesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReferencesDeletePopupComponent,
    resolve: {
      references: ReferencesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cdbemApp.references.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
