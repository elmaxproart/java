package Sea.incubator.sgdeb.repository;


import Sea.incubator.sgdeb.model.Filiere;
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
public interface GrilleRepository extends JpaRepository<Grille, UUID> {
    @Query("SELECT g FROM Grille g WHERE g.filiere.id = :filiere_id")
    List<Grille> findAllByFiliereId(@Param("filiere_id") UUID id);
    @Query("SELECT g FROM Grille g WHERE g.filiere.id = :filiere_id AND g.dateSuppression IS NULL")
    List<Grille> findAllActiveByFiliereId(@Param("filiere_id") UUID filiereId);
    Optional<Grille> findByLibelleAndDateSuppressionIsNull(String libelle);
    Optional<Grille> findByCodeAndDateSuppressionIsNull(String code);
    Optional<Grille> findByIdAndDateSuppressionIsNull(UUID id);
    List<Grille> findByFiliere(Filiere filiere);
}
