import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ResponsavelService } from 'app/entities/responsavel/responsavel.service';
import { IResponsavel, Responsavel } from 'app/shared/model/responsavel.model';
import { Vinculo } from 'app/shared/model/enumerations/vinculo.model';
import { Genero } from 'app/shared/model/enumerations/genero.model';

describe('Service Tests', () => {
  describe('Responsavel Service', () => {
    let injector: TestBed;
    let service: ResponsavelService;
    let httpMock: HttpTestingController;
    let elemDefault: IResponsavel;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ResponsavelService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Responsavel(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        currentDate,
        Vinculo.FILHO,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Genero.MASCULINO,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataNascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Responsavel', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataNascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascimento: currentDate
          },
          returnedFromService
        );
        service
          .create(new Responsavel(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Responsavel', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            sobrenome: 'BBBBBB',
            email: 'BBBBBB',
            enabled: true,
            dataNascimento: currentDate.format(DATE_FORMAT),
            vinculo: 'BBBBBB',
            cpf: 'BBBBBB',
            rg: 'BBBBBB',
            endereco: 'BBBBBB',
            cidade: 'BBBBBB',
            genero: 'BBBBBB',
            cep: 'BBBBBB',
            photo: 'BBBBBB',
            obs: 'BBBBBB',
            telefones: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNascimento: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Responsavel', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            sobrenome: 'BBBBBB',
            email: 'BBBBBB',
            enabled: true,
            dataNascimento: currentDate.format(DATE_FORMAT),
            vinculo: 'BBBBBB',
            cpf: 'BBBBBB',
            rg: 'BBBBBB',
            endereco: 'BBBBBB',
            cidade: 'BBBBBB',
            genero: 'BBBBBB',
            cep: 'BBBBBB',
            photo: 'BBBBBB',
            obs: 'BBBBBB',
            telefones: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascimento: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Responsavel', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
