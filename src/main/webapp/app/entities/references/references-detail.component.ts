import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReferences } from 'app/shared/model/references.model';

@Component({
  selector: 'jhi-references-detail',
  templateUrl: './references-detail.component.html'
})
export class ReferencesDetailComponent implements OnInit {
  references: IReferences;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ references }) => {
      this.references = references;
    });
  }

  previousState() {
    window.history.back();
  }
}
