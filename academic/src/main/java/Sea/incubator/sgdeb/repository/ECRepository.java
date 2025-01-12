package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.EC;
import Sea.incubator.sgdeb.model.UE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ECRepository extends JpaRepository<EC, UUID> {
    @Query("SELECT e FROM EC e WHERE e.UE.id =:ue_id")
    List<EC> findAllByUEId(@Param("ue_id") UUID ueId);
    @Query("SELECT e FROM EC e WHERE e.UE.id = :ue_id AND e.dateSuppression IS NULL")
    List<EC> findAllActiveByUEId(@Param("ue_id") UUID ueId);
    Optional<EC> findByCodeEcAndDateSuppressionIsNull(String codeEc);
    Optional<EC> findByIdAndDateSuppressionIsNull(UUID id);
    List<EC> findByUE(UE UE);
}