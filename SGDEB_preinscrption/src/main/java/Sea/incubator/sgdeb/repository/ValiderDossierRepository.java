package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.ValiderDossier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ValiderDossierRepository extends JpaRepository<ValiderDossier ,UUID> {
}
