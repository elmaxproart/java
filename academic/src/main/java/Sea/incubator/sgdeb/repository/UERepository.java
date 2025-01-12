package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.Grille;
import Sea.incubator.sgdeb.model.UE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UERepository extends JpaRepository<UE, UUID> {
    @Query("SELECT u FROM UE u WHERE u.grille.id =:grille_id")
    List<UE> findAllByGrilleId(@Param("grille_id") UUID grilleId);
    @Query("SELECT u FROM UE u WHERE u.grille.id = :grille_id AND u.dateSuppression IS NULL")
    List<UE> findAllActiveByGrilleId(@Param("grille_id") UUID grilleId);
    Optional<UE> findByCodeAndDateSuppressionIsNull(String code);


    Optional<UE> findByIdAndDateSuppressionIsNull(UUID id);

    List<UE> findByGrille(Grille grille);
}
