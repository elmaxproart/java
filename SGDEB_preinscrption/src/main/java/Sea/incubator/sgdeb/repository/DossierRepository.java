package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.DossierCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DossierRepository extends JpaRepository<DossierCandidat, UUID> {
    Optional<DossierCandidat> findByCodePreinscription(long codePreinscription);


/*
    @Query("SELECT c.regionOrigine, COUNT(c) AS effectif FROM DossierCandidat c GROUP BY c.regionOrigine")
    List<Object[]> countCandidatsByRegionOrigine();
@Query("SELECT c.regionOrigine, COUNT(c) FROM DossierCandidat c GROUP BY c.regionOrigine")
    List<Object[]> countCandidatsByRegionOrigine();
 */
    @Query("SELECT c.regionOrigine, COUNT(c) FROM DossierCandidat c GROUP BY c.regionOrigine")
    List<Object[]> countCandidatsByRegionOrigine();

    boolean existsByCodePreinscription(long code);

    @Transactional
    @Modifying
    @Query("update DossierCandidat d set d.admin = ?1")
    int updateAdminBy(boolean admin);
}
