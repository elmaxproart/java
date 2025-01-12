package Sea.incubator.sgdeb.external;


import Sea.incubator.sgdeb.model.enumType.TypeEval;
import lombok.Data;

import java.util.UUID;

@Data
public class GrilleExamenDTO {

    private UUID id;


    private TypeEval typeEval;
    private int noteSur;
    private int statut;
    private String session;
    private int semestre;


}