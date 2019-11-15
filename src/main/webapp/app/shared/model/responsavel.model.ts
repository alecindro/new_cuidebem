import { Moment } from 'moment';
import { Vinculo } from 'app/shared/model/enumerations/vinculo.model';
import { Genero } from 'app/shared/model/enumerations/genero.model';

export interface IResponsavel {
  id?: number;
  nome?: string;
  sobrenome?: string;
  email?: string;
  enabled?: boolean;
  dataNascimento?: Moment;
  vinculo?: Vinculo;
  cpf?: string;
  rg?: string;
  endereco?: string;
  cidade?: string;
  genero?: Genero;
  cep?: string;
  photo?: string;
  obs?: string;
  telefones?: string;
}

export class Responsavel implements IResponsavel {
  constructor(
    public id?: number,
    public nome?: string,
    public sobrenome?: string,
    public email?: string,
    public enabled?: boolean,
    public dataNascimento?: Moment,
    public vinculo?: Vinculo,
    public cpf?: string,
    public rg?: string,
    public endereco?: string,
    public cidade?: string,
    public genero?: Genero,
    public cep?: string,
    public photo?: string,
    public obs?: string,
    public telefones?: string
  ) {
    this.enabled = this.enabled || false;
  }
}
