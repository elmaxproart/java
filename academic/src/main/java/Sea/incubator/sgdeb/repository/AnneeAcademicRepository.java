package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.AnneeAcademic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnneeAcademicRepository extends JpaRepository<AnneeAcademic, UUID> {
    List<AnneeAcademic> findByDateSuppressionIsNull();
    Optional<AnneeAcademic> findByIdAndDateSuppressionIsNull(UUID id);
}
