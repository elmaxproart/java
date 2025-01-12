package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, UUID> {
    List<Departement> findByDateSuppressionIsNull();
    Optional<Departement> findByNomDepartmentAndDateSuppressionIsNull(String nomDepartment);
    Optional<Departement> findByIdAndDateSuppressionIsNull(UUID id);
}
