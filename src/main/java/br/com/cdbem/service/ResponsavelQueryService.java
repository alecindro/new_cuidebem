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

import br.com.cdbem.domain.Responsavel;
import br.com.cdbem.domain.*; // for static metamodels
import br.com.cdbem.repository.ResponsavelRepository;
import br.com.cdbem.service.dto.ResponsavelCriteria;

/**
 * Service for executing complex queries for {@link Responsavel} entities in the database.
 * The main input is a {@link ResponsavelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Responsavel} or a {@link Page} of {@link Responsavel} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResponsavelQueryService extends QueryService<Responsavel> {

    private final Logger log = LoggerFactory.getLogger(ResponsavelQueryService.class);

    private final ResponsavelRepository responsavelRepository;

    public ResponsavelQueryService(ResponsavelRepository responsavelRepository) {
        this.responsavelRepository = responsavelRepository;
    }

    /**
     * Return a {@link List} of {@link Responsavel} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Responsavel> findByCriteria(ResponsavelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Responsavel> specification = createSpecification(criteria);
        return responsavelRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Responsavel} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Responsavel> findByCriteria(ResponsavelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Responsavel> specification = createSpecification(criteria);
        return responsavelRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResponsavelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Responsavel> specification = createSpecification(criteria);
        return responsavelRepository.count(specification);
    }

    /**
     * Function to convert {@link ResponsavelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Responsavel> createSpecification(ResponsavelCriteria criteria) {
        Specification<Responsavel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Responsavel_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Responsavel_.nome));
            }
            if (criteria.getSobrenome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSobrenome(), Responsavel_.sobrenome));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Responsavel_.email));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), Responsavel_.enabled));
            }
            if (criteria.getDataNascimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataNascimento(), Responsavel_.dataNascimento));
            }
            if (criteria.getVinculo() != null) {
                specification = specification.and(buildSpecification(criteria.getVinculo(), Responsavel_.vinculo));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpf(), Responsavel_.cpf));
            }
            if (criteria.getRg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRg(), Responsavel_.rg));
            }
            if (criteria.getEndereco() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndereco(), Responsavel_.endereco));
            }
            if (criteria.getCidade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCidade(), Responsavel_.cidade));
            }
            if (criteria.getGenero() != null) {
                specification = specification.and(buildSpecification(criteria.getGenero(), Responsavel_.genero));
            }
            if (criteria.getCep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCep(), Responsavel_.cep));
            }
            if (criteria.getPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoto(), Responsavel_.photo));
            }
            if (criteria.getObs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObs(), Responsavel_.obs));
            }
            if (criteria.getTelefones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefones(), Responsavel_.telefones));
            }
        }
        return specification;
    }
}
