package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code,Long> {
    Optional<Code> findByCodePaiement(long codePaiement);
}
