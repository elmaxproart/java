package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.GrilleExamen;
import Sea.incubator.sgdeb.model.Importation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.UUID;

public interface ImportationRepository extends JpaRepository<Importation, UUID> {
    Importation findByGrille(GrilleExamen grille);



    Importation findByCodeUEAndCodeFilAndDateImportationAndGrilleId(String codeUE, String codeFiliere, Date dateImportation, UUID id);
}
