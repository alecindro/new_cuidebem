import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CdbemSharedModule } from 'app/shared/shared.module';
import { PacienteComponent } from './paciente.component';
import { PacienteDetailComponent } from './paciente-detail.component';
import { PacienteUpdateComponent } from './paciente-update.component';
import { PacienteDeletePopupComponent, PacienteDeleteDialogComponent } from './paciente-delete-dialog.component';
import { pacienteRoute, pacientePopupRoute } from './paciente.route';

const ENTITY_STATES = [...pacienteRoute, ...pacientePopupRoute];

@NgModule({
  imports: [CdbemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PacienteComponent,
    PacienteDetailComponent,
    PacienteUpdateComponent,
    PacienteDeleteDialogComponent,
    PacienteDeletePopupComponent
  ],
  entryComponents: [PacienteDeleteDialogComponent]
})
export class CdbemPacienteModule {}
