import { Moment } from 'moment';
import { IResponsavel } from 'app/shared/model/responsavel.model';
import { Genero } from 'app/shared/model/enumerations/genero.model';
import { TipoEstadia } from 'app/shared/model/enumerations/tipo-estadia.model';

export interface IPaciente {
  id?: number;
  nome?: string;
  apelido?: string;
  genero?: Genero;
  enabled?: boolean;
  dataNascimento?: Moment;
  tipoEstadia?: TipoEstadia;
  photo?: string;
  patologias?: string;
  dataCadastro?: Moment;
  checkin?: boolean;
  responsavels?: IResponsavel[];
}

export class Paciente implements IPaciente {
  constructor(
    public id?: number,
    public nome?: string,
    public apelido?: string,
    public genero?: Genero,
    public enabled?: boolean,
    public dataNascimento?: Moment,
    public tipoEstadia?: TipoEstadia,
    public photo?: string,
    public patologias?: string,
    public dataCadastro?: Moment,
    public checkin?: boolean,
    public responsavels?: IResponsavel[]
  ) {
    this.enabled = this.enabled || false;
    this.checkin = this.checkin || false;
  }
}
