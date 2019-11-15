import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CdbemTestModule } from '../../../test.module';
import { ReferencesDeleteDialogComponent } from 'app/entities/references/references-delete-dialog.component';
import { ReferencesService } from 'app/entities/references/references.service';

describe('Component Tests', () => {
  describe('References Management Delete Component', () => {
    let comp: ReferencesDeleteDialogComponent;
    let fixture: ComponentFixture<ReferencesDeleteDialogComponent>;
    let service: ReferencesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CdbemTestModule],
        declarations: [ReferencesDeleteDialogComponent]
      })
        .overrideTemplate(ReferencesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReferencesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReferencesService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
