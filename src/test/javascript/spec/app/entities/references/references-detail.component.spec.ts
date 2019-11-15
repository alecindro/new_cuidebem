import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CdbemTestModule } from '../../../test.module';
import { ReferencesDetailComponent } from 'app/entities/references/references-detail.component';
import { References } from 'app/shared/model/references.model';

describe('Component Tests', () => {
  describe('References Management Detail Component', () => {
    let comp: ReferencesDetailComponent;
    let fixture: ComponentFixture<ReferencesDetailComponent>;
    const route = ({ data: of({ references: new References(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CdbemTestModule],
        declarations: [ReferencesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReferencesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReferencesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.references).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
