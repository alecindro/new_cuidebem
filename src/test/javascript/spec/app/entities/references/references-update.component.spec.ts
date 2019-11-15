import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CdbemTestModule } from '../../../test.module';
import { ReferencesUpdateComponent } from 'app/entities/references/references-update.component';
import { ReferencesService } from 'app/entities/references/references.service';
import { References } from 'app/shared/model/references.model';

describe('Component Tests', () => {
  describe('References Management Update Component', () => {
    let comp: ReferencesUpdateComponent;
    let fixture: ComponentFixture<ReferencesUpdateComponent>;
    let service: ReferencesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CdbemTestModule],
        declarations: [ReferencesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReferencesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReferencesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReferencesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new References(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new References();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
