package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.model.PvDetails;
import Sea.incubator.sgdeb.model.PvDetailsRecap;
import Sea.incubator.sgdeb.model.PvRecap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PvDetailsRecapRepository extends JpaRepository<PvDetailsRecap, UUID> {
  Optional<PvDetailsRecap> findByMatricule(String matricule);
}