package br.com.cdbem.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.com.cdbem.domain.References;
import br.com.cdbem.domain.*; // for static metamodels
import br.com.cdbem.repository.ReferencesRepository;
import br.com.cdbem.service.dto.ReferencesCriteria;

/**
 * Service for executing complex queries for {@link References} entities in the database.
 * The main input is a {@link ReferencesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link References} or a {@link Page} of {@link References} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReferencesQueryService extends QueryService<References> {

    private final Logger log = LoggerFactory.getLogger(ReferencesQueryService.class);

    private final ReferencesRepository referencesRepository;

    public ReferencesQueryService(ReferencesRepository referencesRepository) {
        this.referencesRepository = referencesRepository;
    }

    /**
     * Return a {@link List} of {@link References} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<References> findByCriteria(ReferencesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<References> specification = createSpecification(criteria);
        return referencesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link References} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<References> findByCriteria(ReferencesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<References> specification = createSpecification(criteria);
        return referencesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReferencesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<References> specification = createSpecification(criteria);
        return referencesRepository.count(specification);
    }

    /**
     * Function to convert {@link ReferencesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<References> createSpecification(ReferencesCriteria criteria) {
        Specification<References> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), References_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), References_.tipo));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), References_.value));
            }
        }
        return specification;
    }
}
