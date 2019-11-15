import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReferences } from 'app/shared/model/references.model';
import { ReferencesService } from './references.service';

@Component({
  selector: 'jhi-references-delete-dialog',
  templateUrl: './references-delete-dialog.component.html'
})
export class ReferencesDeleteDialogComponent {
  references: IReferences;

  constructor(
    protected referencesService: ReferencesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.referencesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'referencesListModification',
        content: 'Deleted an references'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-references-delete-popup',
  template: ''
})
export class ReferencesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ references }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReferencesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.references = references;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/references', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/references', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
