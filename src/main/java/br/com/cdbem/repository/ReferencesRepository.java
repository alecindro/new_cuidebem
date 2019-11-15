package br.com.cdbem.repository;
import br.com.cdbem.domain.References;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the References entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferencesRepository extends JpaRepository<References, Long>, JpaSpecificationExecutor<References> {

}
