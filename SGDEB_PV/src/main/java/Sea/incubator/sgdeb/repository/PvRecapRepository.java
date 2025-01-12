package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.PvRecap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PvRecapRepository extends JpaRepository<PvRecap, UUID> {
    @Query("SELECT p FROM PvRecap p LEFT JOIN FETCH p.ueGradesSur100")
    List<PvRecap> findAllWithUeGradesSur100();

    Optional<PvRecap> findByMatricule(String matricule);
}
