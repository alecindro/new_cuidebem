import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CdbemSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [CdbemSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class CdbemHomeModule {}
