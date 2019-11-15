import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'responsavel',
        loadChildren: () => import('./responsavel/responsavel.module').then(m => m.CdbemResponsavelModule)
      },
      {
        path: 'paciente',
        loadChildren: () => import('./paciente/paciente.module').then(m => m.CdbemPacienteModule)
      },
      {
        path: 'references',
        loadChildren: () => import('./references/references.module').then(m => m.CdbemReferencesModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CdbemEntityModule {}
