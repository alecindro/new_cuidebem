package br.com.cdbem.web.rest;

import br.com.cdbem.domain.References;
import br.com.cdbem.service.ReferencesService;
import br.com.cdbem.web.rest.errors.BadRequestAlertException;
import br.com.cdbem.service.dto.ReferencesCriteria;
import br.com.cdbem.service.ReferencesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.cdbem.domain.References}.
 */
@RestController
@RequestMapping("/api")
public class ReferencesResource {

    private final Logger log = LoggerFactory.getLogger(ReferencesResource.class);

    private static final String ENTITY_NAME = "references";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferencesService referencesService;

    private final ReferencesQueryService referencesQueryService;

    public ReferencesResource(ReferencesService referencesService, ReferencesQueryService referencesQueryService) {
        this.referencesService = referencesService;
        this.referencesQueryService = referencesQueryService;
    }

    /**
     * {@code POST  /references} : Create a new references.
     *
     * @param references the references to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new references, or with status {@code 400 (Bad Request)} if the references has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/references")
    public ResponseEntity<References> createReferences(@RequestBody References references) throws URISyntaxException {
        log.debug("REST request to save References : {}", references);
        if (references.getId() != null) {
            throw new BadRequestAlertException("A new references cannot already have an ID", ENTITY_NAME, "idexists");
        }
        References result = referencesService.save(references);
        return ResponseEntity.created(new URI("/api/references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /references} : Updates an existing references.
     *
     * @param references the references to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated references,
     * or with status {@code 400 (Bad Request)} if the references is not valid,
     * or with status {@code 500 (Internal Server Error)} if the references couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/references")
    public ResponseEntity<References> updateReferences(@RequestBody References references) throws URISyntaxException {
        log.debug("REST request to update References : {}", references);
        if (references.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        References result = referencesService.save(references);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, references.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /references} : get all the references.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of references in body.
     */
    @GetMapping("/references")
    public ResponseEntity<List<References>> getAllReferences(ReferencesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get References by criteria: {}", criteria);
        Page<References> page = referencesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /references/count} : count all the references.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/references/count")
    public ResponseEntity<Long> countReferences(ReferencesCriteria criteria) {
        log.debug("REST request to count References by criteria: {}", criteria);
        return ResponseEntity.ok().body(referencesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /references/:id} : get the "id" references.
     *
     * @param id the id of the references to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the references, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/references/{id}")
    public ResponseEntity<References> getReferences(@PathVariable Long id) {
        log.debug("REST request to get References : {}", id);
        Optional<References> references = referencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(references);
    }

    /**
     * {@code DELETE  /references/:id} : delete the "id" references.
     *
     * @param id the id of the references to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/references/{id}")
    public ResponseEntity<Void> deleteReferences(@PathVariable Long id) {
        log.debug("REST request to delete References : {}", id);
        referencesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
