package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.Exportation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExportationRepository extends JpaRepository<Exportation, UUID> {
}
