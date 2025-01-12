package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.external.UEDTO;
import Sea.incubator.sgdeb.external.enumType.TypeEval;
import Sea.incubator.sgdeb.model.GrilleExamen;

import java.util.List;
import java.util.UUID;

public interface ExamenService {
    GrilleExamen createExamen(GrilleExamen grilleExamenEntity, String codeUE);

    GrilleExamen updateExamen(UUID id, GrilleExamen grilleExamen);

    List<GrilleExamen> getAllExamen();

    GrilleExamen getExamenById(UUID id);

    void deleteExamen(UUID id);

    UEDTO getUeByCode(String code);


    GrilleExamen getGrilles(TypeEval typeEval, String codeFiliere, String codeUE);

}
