package br.com.cdbem.web.rest;

import br.com.cdbem.domain.Responsavel;
import br.com.cdbem.service.ResponsavelService;
import br.com.cdbem.web.rest.errors.BadRequestAlertException;
import br.com.cdbem.service.dto.ResponsavelCriteria;
import br.com.cdbem.service.ResponsavelQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.cdbem.domain.Responsavel}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelResource.class);

    private static final String ENTITY_NAME = "responsavel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelService responsavelService;

    private final ResponsavelQueryService responsavelQueryService;

    public ResponsavelResource(ResponsavelService responsavelService, ResponsavelQueryService responsavelQueryService) {
        this.responsavelService = responsavelService;
        this.responsavelQueryService = responsavelQueryService;
    }

    /**
     * {@code POST  /responsavels} : Create a new responsavel.
     *
     * @param responsavel the responsavel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavel, or with status {@code 400 (Bad Request)} if the responsavel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavels")
    public ResponseEntity<Responsavel> createResponsavel(@Valid @RequestBody Responsavel responsavel) throws URISyntaxException {
        log.debug("REST request to save Responsavel : {}", responsavel);
        if (responsavel.getId() != null) {
            throw new BadRequestAlertException("A new responsavel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Responsavel result = responsavelService.save(responsavel);
        return ResponseEntity.created(new URI("/api/responsavels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavels} : Updates an existing responsavel.
     *
     * @param responsavel the responsavel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavel,
     * or with status {@code 400 (Bad Request)} if the responsavel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavels")
    public ResponseEntity<Responsavel> updateResponsavel(@Valid @RequestBody Responsavel responsavel) throws URISyntaxException {
        log.debug("REST request to update Responsavel : {}", responsavel);
        if (responsavel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Responsavel result = responsavelService.save(responsavel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /responsavels} : get all the responsavels.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavels in body.
     */
    @GetMapping("/responsavels")
    public ResponseEntity<List<Responsavel>> getAllResponsavels(ResponsavelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Responsavels by criteria: {}", criteria);
        Page<Responsavel> page = responsavelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /responsavels/count} : count all the responsavels.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/responsavels/count")
    public ResponseEntity<Long> countResponsavels(ResponsavelCriteria criteria) {
        log.debug("REST request to count Responsavels by criteria: {}", criteria);
        return ResponseEntity.ok().body(responsavelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /responsavels/:id} : get the "id" responsavel.
     *
     * @param id the id of the responsavel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavels/{id}")
    public ResponseEntity<Responsavel> getResponsavel(@PathVariable Long id) {
        log.debug("REST request to get Responsavel : {}", id);
        Optional<Responsavel> responsavel = responsavelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavel);
    }

    /**
     * {@code DELETE  /responsavels/:id} : delete the "id" responsavel.
     *
     * @param id the id of the responsavel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavels/{id}")
    public ResponseEntity<Void> deleteResponsavel(@PathVariable Long id) {
        log.debug("REST request to delete Responsavel : {}", id);
        responsavelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
