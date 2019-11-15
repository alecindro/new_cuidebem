import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IReferences, References } from 'app/shared/model/references.model';
import { ReferencesService } from './references.service';

@Component({
  selector: 'jhi-references-update',
  templateUrl: './references-update.component.html'
})
export class ReferencesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    tipo: [],
    value: []
  });

  constructor(protected referencesService: ReferencesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ references }) => {
      this.updateForm(references);
    });
  }

  updateForm(references: IReferences) {
    this.editForm.patchValue({
      id: references.id,
      tipo: references.tipo,
      value: references.value
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const references = this.createFromForm();
    if (references.id !== undefined) {
      this.subscribeToSaveResponse(this.referencesService.update(references));
    } else {
      this.subscribeToSaveResponse(this.referencesService.create(references));
    }
  }

  private createFromForm(): IReferences {
    return {
      ...new References(),
      id: this.editForm.get(['id']).value,
      tipo: this.editForm.get(['tipo']).value,
      value: this.editForm.get(['value']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferences>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
