package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.model.AnneeAcademic;
import Sea.incubator.sgdeb.model.enumType.Niveau;
import lombok.Data;

import java.util.List;
import java.util.UUID;


/**
 * The type Grille dto.
 */
@Data
public class GrilleDTO {
    private UUID id;
    private String libelle;
    private FiliereDTO filiere;
    private AnneeAcademicDto anneeAcademic;
    private String code;
    private Niveau niveau;


}
