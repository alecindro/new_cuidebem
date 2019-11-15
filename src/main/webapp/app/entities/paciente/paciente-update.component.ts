import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPaciente, Paciente } from 'app/shared/model/paciente.model';
import { PacienteService } from './paciente.service';

@Component({
  selector: 'jhi-paciente-update',
  templateUrl: './paciente-update.component.html'
})
export class PacienteUpdateComponent implements OnInit {
  isSaving: boolean;
  dataNascimentoDp: any;
  dataCadastroDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [],
    apelido: [],
    genero: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
    dataNascimento: [],
    tipoEstadia: [],
    photo: [],
    patologias: [],
    dataCadastro: [],
    checkin: [null, [Validators.required]]
  });

  constructor(protected pacienteService: PacienteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paciente }) => {
      this.updateForm(paciente);
    });
  }

  updateForm(paciente: IPaciente) {
    this.editForm.patchValue({
      id: paciente.id,
      nome: paciente.nome,
      apelido: paciente.apelido,
      genero: paciente.genero,
      enabled: paciente.enabled,
      dataNascimento: paciente.dataNascimento,
      tipoEstadia: paciente.tipoEstadia,
      photo: paciente.photo,
      patologias: paciente.patologias,
      dataCadastro: paciente.dataCadastro,
      checkin: paciente.checkin
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paciente = this.createFromForm();
    if (paciente.id !== undefined) {
      this.subscribeToSaveResponse(this.pacienteService.update(paciente));
    } else {
      this.subscribeToSaveResponse(this.pacienteService.create(paciente));
    }
  }

  private createFromForm(): IPaciente {
    return {
      ...new Paciente(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      apelido: this.editForm.get(['apelido']).value,
      genero: this.editForm.get(['genero']).value,
      enabled: this.editForm.get(['enabled']).value,
      dataNascimento: this.editForm.get(['dataNascimento']).value,
      tipoEstadia: this.editForm.get(['tipoEstadia']).value,
      photo: this.editForm.get(['photo']).value,
      patologias: this.editForm.get(['patologias']).value,
      dataCadastro: this.editForm.get(['dataCadastro']).value,
      checkin: this.editForm.get(['checkin']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaciente>>) {
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
