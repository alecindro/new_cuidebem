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

import br.com.cdbem.domain.Paciente;
import br.com.cdbem.domain.*; // for static metamodels
import br.com.cdbem.repository.PacienteRepository;
import br.com.cdbem.service.dto.PacienteCriteria;

/**
 * Service for executing complex queries for {@link Paciente} entities in the database.
 * The main input is a {@link PacienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Paciente} or a {@link Page} of {@link Paciente} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PacienteQueryService extends QueryService<Paciente> {

    private final Logger log = LoggerFactory.getLogger(PacienteQueryService.class);

    private final PacienteRepository pacienteRepository;

    public PacienteQueryService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Return a {@link List} of {@link Paciente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Paciente> findByCriteria(PacienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Paciente> specification = createSpecification(criteria);
        return pacienteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Paciente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Paciente> findByCriteria(PacienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Paciente> specification = createSpecification(criteria);
        return pacienteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PacienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Paciente> specification = createSpecification(criteria);
        return pacienteRepository.count(specification);
    }

    /**
     * Function to convert {@link PacienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Paciente> createSpecification(PacienteCriteria criteria) {
        Specification<Paciente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Paciente_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Paciente_.nome));
            }
            if (criteria.getApelido() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApelido(), Paciente_.apelido));
            }
            if (criteria.getGenero() != null) {
                specification = specification.and(buildSpecification(criteria.getGenero(), Paciente_.genero));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), Paciente_.enabled));
            }
            if (criteria.getDataNascimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataNascimento(), Paciente_.dataNascimento));
            }
            if (criteria.getTipoEstadia() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoEstadia(), Paciente_.tipoEstadia));
            }
            if (criteria.getPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoto(), Paciente_.photo));
            }
            if (criteria.getPatologias() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatologias(), Paciente_.patologias));
            }
            if (criteria.getDataCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCadastro(), Paciente_.dataCadastro));
            }
            if (criteria.getCheckin() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckin(), Paciente_.checkin));
            }
            if (criteria.getResponsavelId() != null) {
                specification = specification.and(buildSpecification(criteria.getResponsavelId(),
                    root -> root.join(Paciente_.responsavels, JoinType.LEFT).get(Responsavel_.id)));
            }
        }
        return specification;
    }
}
