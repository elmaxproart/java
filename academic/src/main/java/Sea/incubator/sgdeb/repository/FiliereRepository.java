package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.Departement;
import Sea.incubator.sgdeb.model.Filiere;
import Sea.incubator.sgdeb.model.UE;
import Sea.incubator.sgdeb.model.enumType.Faculte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, UUID> {
    @Query("SELECT f FROM Filiere f WHERE f.departement.id = :departement_id")
    List<Filiere> findAllByDepartmentId(@Param("departement_id") UUID departement_id);
    @Query("SELECT f FROM Filiere f WHERE f.departement.id = :departement_id AND f.dateSuppression IS NULL")
    List<Filiere> findAllActiveByDepartmentId(@Param("departement_id") UUID departementId);
    Optional<Filiere> findByLibelleAndDateSuppressionIsNull(String libelle);
    Optional<Filiere> findByCodeAndDateSuppressionIsNull(String code);
    Optional<Filiere> findByIdAndDateSuppressionIsNull(UUID id);
    List<Filiere> findByDepartement(Departement departement);
    List<Filiere> findByDateSuppressionIsNull();
    List<Filiere> findByFaculteAndDateSuppressionIsNull(Faculte faculte);



}
