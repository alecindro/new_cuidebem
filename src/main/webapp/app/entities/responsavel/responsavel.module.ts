import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CdbemSharedModule } from 'app/shared/shared.module';
import { ResponsavelComponent } from './responsavel.component';
import { ResponsavelDetailComponent } from './responsavel-detail.component';
import { ResponsavelUpdateComponent } from './responsavel-update.component';
import { ResponsavelDeletePopupComponent, ResponsavelDeleteDialogComponent } from './responsavel-delete-dialog.component';
import { responsavelRoute, responsavelPopupRoute } from './responsavel.route';

const ENTITY_STATES = [...responsavelRoute, ...responsavelPopupRoute];

@NgModule({
  imports: [CdbemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ResponsavelComponent,
    ResponsavelDetailComponent,
    ResponsavelUpdateComponent,
    ResponsavelDeleteDialogComponent,
    ResponsavelDeletePopupComponent
  ],
  entryComponents: [ResponsavelDeleteDialogComponent]
})
export class CdbemResponsavelModule {}
