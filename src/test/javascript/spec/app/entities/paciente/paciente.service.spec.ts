import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PacienteService } from 'app/entities/paciente/paciente.service';
import { IPaciente, Paciente } from 'app/shared/model/paciente.model';
import { Genero } from 'app/shared/model/enumerations/genero.model';
import { TipoEstadia } from 'app/shared/model/enumerations/tipo-estadia.model';

describe('Service Tests', () => {
  describe('Paciente Service', () => {
    let injector: TestBed;
    let service: PacienteService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaciente;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PacienteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Paciente(
        0,
        'AAAAAAA',
        'AAAAAAA',
        Genero.MASCULINO,
        false,
        currentDate,
        TipoEstadia.PERIODICO,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataNascimento: currentDate.format(DATE_FORMAT),
            dataCadastro: currentDate.format(DATE_FORMAT)
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

      it('should create a Paciente', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataNascimento: currentDate.format(DATE_FORMAT),
            dataCadastro: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascimento: currentDate,
            dataCadastro: currentDate
          },
          returnedFromService
        );
        service
          .create(new Paciente(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Paciente', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            apelido: 'BBBBBB',
            genero: 'BBBBBB',
            enabled: true,
            dataNascimento: currentDate.format(DATE_FORMAT),
            tipoEstadia: 'BBBBBB',
            photo: 'BBBBBB',
            patologias: 'BBBBBB',
            dataCadastro: currentDate.format(DATE_FORMAT),
            checkin: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNascimento: currentDate,
            dataCadastro: currentDate
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

      it('should return a list of Paciente', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            apelido: 'BBBBBB',
            genero: 'BBBBBB',
            enabled: true,
            dataNascimento: currentDate.format(DATE_FORMAT),
            tipoEstadia: 'BBBBBB',
            photo: 'BBBBBB',
            patologias: 'BBBBBB',
            dataCadastro: currentDate.format(DATE_FORMAT),
            checkin: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascimento: currentDate,
            dataCadastro: currentDate
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

      it('should delete a Paciente', () => {
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
