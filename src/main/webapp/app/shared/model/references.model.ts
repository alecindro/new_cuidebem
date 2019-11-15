export interface IReferences {
  id?: number;
  tipo?: string;
  value?: string;
}

export class References implements IReferences {
  constructor(public id?: number, public tipo?: string, public value?: string) {}
}
