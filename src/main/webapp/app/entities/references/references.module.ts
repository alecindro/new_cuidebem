import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CdbemSharedModule } from 'app/shared/shared.module';
import { ReferencesComponent } from './references.component';
import { ReferencesDetailComponent } from './references-detail.component';
import { ReferencesUpdateComponent } from './references-update.component';
import { ReferencesDeletePopupComponent, ReferencesDeleteDialogComponent } from './references-delete-dialog.component';
import { referencesRoute, referencesPopupRoute } from './references.route';

const ENTITY_STATES = [...referencesRoute, ...referencesPopupRoute];

@NgModule({
  imports: [CdbemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReferencesComponent,
    ReferencesDetailComponent,
    ReferencesUpdateComponent,
    ReferencesDeleteDialogComponent,
    ReferencesDeletePopupComponent
  ],
  entryComponents: [ReferencesDeleteDialogComponent]
})
export class CdbemReferencesModule {}
