package Sea.incubator.sgdeb.external;


import lombok.Data;

import java.util.UUID;

@Data
public class GrilleExamenDTO {

    private UUID id;

    private UEDTO uEdto;
    private FiliereDTO filiereDTO;
    private TypeEval typeEval;
    private int noteSur;
    private int statut;
    private String session;
    private String anneeAcademic;

}
