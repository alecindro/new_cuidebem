import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IResponsavel, Responsavel } from 'app/shared/model/responsavel.model';
import { ResponsavelService } from './responsavel.service';

@Component({
  selector: 'jhi-responsavel-update',
  templateUrl: './responsavel-update.component.html'
})
export class ResponsavelUpdateComponent implements OnInit {
  isSaving: boolean;
  dataNascimentoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [],
    sobrenome: [],
    email: [],
    enabled: [null, [Validators.required]],
    dataNascimento: [],
    vinculo: [],
    cpf: [],
    rg: [],
    endereco: [],
    cidade: [],
    genero: [],
    cep: [],
    photo: [],
    obs: [],
    telefones: []
  });

  constructor(protected responsavelService: ResponsavelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ responsavel }) => {
      this.updateForm(responsavel);
    });
  }

  updateForm(responsavel: IResponsavel) {
    this.editForm.patchValue({
      id: responsavel.id,
      nome: responsavel.nome,
      sobrenome: responsavel.sobrenome,
      email: responsavel.email,
      enabled: responsavel.enabled,
      dataNascimento: responsavel.dataNascimento,
      vinculo: responsavel.vinculo,
      cpf: responsavel.cpf,
      rg: responsavel.rg,
      endereco: responsavel.endereco,
      cidade: responsavel.cidade,
      genero: responsavel.genero,
      cep: responsavel.cep,
      photo: responsavel.photo,
      obs: responsavel.obs,
      telefones: responsavel.telefones
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const responsavel = this.createFromForm();
    if (responsavel.id !== undefined) {
      this.subscribeToSaveResponse(this.responsavelService.update(responsavel));
    } else {
      this.subscribeToSaveResponse(this.responsavelService.create(responsavel));
    }
  }

  private createFromForm(): IResponsavel {
    return {
      ...new Responsavel(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      sobrenome: this.editForm.get(['sobrenome']).value,
      email: this.editForm.get(['email']).value,
      enabled: this.editForm.get(['enabled']).value,
      dataNascimento: this.editForm.get(['dataNascimento']).value,
      vinculo: this.editForm.get(['vinculo']).value,
      cpf: this.editForm.get(['cpf']).value,
      rg: this.editForm.get(['rg']).value,
      endereco: this.editForm.get(['endereco']).value,
      cidade: this.editForm.get(['cidade']).value,
      genero: this.editForm.get(['genero']).value,
      cep: this.editForm.get(['cep']).value,
      photo: this.editForm.get(['photo']).value,
      obs: this.editForm.get(['obs']).value,
      telefones: this.editForm.get(['telefones']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavel>>) {
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
