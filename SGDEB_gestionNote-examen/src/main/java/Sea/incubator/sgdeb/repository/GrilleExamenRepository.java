package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.external.enumType.TypeEval;
import Sea.incubator.sgdeb.model.GrilleExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface GrilleExamenRepository extends JpaRepository<GrilleExamen, UUID> {

    GrilleExamen findByCodeUEAndCodeFiliereAndTypeEval(String codeFiliere, String codeUE, TypeEval typeEval);

    Optional<GrilleExamen> findByCodeUEAndCodeFiliereAndCreditAndNoteSurAndAnneeAcademicAndTypeEvalAndSessionAndSemestre(String codeUE, String codeFiliere, int credit, int noteSur, String anneeAcademic, TypeEval typeEval, String session, int semestre);


    GrilleExamen findByCodeUEAndCodeFiliereAndTypeEvalAndNoteSur(String codeUE, String codeFiliere, TypeEval typeEval, int noteSur);
}
